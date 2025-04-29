import pandas as pd
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from sqlalchemy import create_engine
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from typing import Optional
import re

# Thông tin kết nối
host = 'localhost'
port = 3306
database = 'itwork'
user = 'root'
password = 'an147258'

# Tạo engine với driver pymysql
connection_url = f"mysql+pymysql://{user}:{password}@{host}:{port}/{database}"

try:
    engine = create_engine(connection_url)
    query = "SELECT * FROM jobs where jobs.is_active = 1"

    app = FastAPI()


    class UserInput(BaseModel):
        currentJob: Optional[str] = None
        note: Optional[str] = None
        currentJobFunction: Optional[int] = None
        address: Optional[str] = None

    # Model dữ liệu user từ Java gửi sang
    class JobInput(BaseModel):
        jobId: int

    # Dummy dữ liệu jobs
    df_jobs = pd.read_sql(query, engine)  # hoặc load từ MySQL như bạn đã làm
    df_jobs = df_jobs.fillna("").copy()

    jobId_to_index = pd.Series(df_jobs.index, index=df_jobs['id']).to_dict()

    @app.post("/recommend")
    def recommend_jobs(job: JobInput):
        if job.jobId not in jobId_to_index:
            raise HTTPException(status_code=404, detail="Job ID không tồn tại hoặc không active")

        features = ['name', 'job_locations', 'job_function_id']

        def combineFeatures(row):
            return str(row['name']) + " " + str(row['job_locations']) + " " + str(row['job_function_id'])

        df_jobs['combineFeatures'] = df_jobs.apply(combineFeatures, axis = 1)
        # print(df_jobs.head())
        tf = TfidfVectorizer()
        tfMatrix = tf.fit_transform(df_jobs['combineFeatures'])
        similar = cosine_similarity(tfMatrix)
        job_index = jobId_to_index[job.jobId]
        similarJob = list(enumerate(similar[job_index]))
        # print(similarJob)
        sortedSimilarJob = sorted(similarJob, key = lambda x: x[1], reverse=True)
        def get_name(index):
            return int((df_jobs[df_jobs.index == index]['id'].values[0]))

        print(get_name(sortedSimilarJob[0][0]))
        number = 5
        ans = []
        for i in range(1,number + 1):
            print(get_name(sortedSimilarJob[i][0]))
            ans.append(get_name(sortedSimilarJob[i][0]))

        return ans


    @app.post("/recommend/user")
    def recommend_jobs(user: UserInput):
        # Tạo cột "combineFeatures" cho từng job từ DB
        df_jobs['combineFeatures'] = df_jobs.apply(
            lambda row: f"{row['name']} {row['description']} {row['job_function_id']} {row['job_locations']}", axis=1
        )

        user_input_parts = []

        if user.currentJob is not None:
            user_input_parts.append(user.currentJob)

        if user.note is not None:
            user_input_parts.append(user.note)

        if user.currentJobFunction is not None:
            user_input_parts.append(str(user.currentJobFunction))

        if user.address is not None:
            user_input_parts.append(str(user.address))

        user_input = " ".join(user_input_parts)

        # Gộp tất cả (job + user) lại để vector hóa
        all_text = df_jobs['combineFeatures'].tolist() + [user_input]

        # Vector hóa và tính độ tương đồng cosine
        tf = TfidfVectorizer()
        tfMatrix = tf.fit_transform(all_text)

        # Chỉ tính cosine giữa user và từng job
        cosine_similarities = cosine_similarity(tfMatrix[-1], tfMatrix[:-1]).flatten()
        sorted_indices = cosine_similarities.argsort()[::-1]

        # Lấy top 5 jobId được recommend
        top_n = 5
        top_job_ids = df_jobs.iloc[sorted_indices[:top_n]]['id'].tolist()

        return top_job_ids

except Exception as e:
    print(f"Lỗi khi truy vấn: {e}")



