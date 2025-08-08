# Web-JobSearch

Chương I. MÔ TẢ, KHẢO SÁT VÀ XÁC ĐỊNH YÊU CẦU TRANG WEB	5
1.1	Mô tả trang web quản lý, tìm kiếm việc làm	5
1.2	Khảo sát, xác định yêu cầu bài toán	5
1.2.1. Khảo sát hiện trạng	5
1.2.2. Mục tiêu của hệ thống	5
1.2.3. Phạm vi hệ thống	6
Chương II. KIẾN THỨC ÁP DỤNG	6
2.1.	Phân tích & Thiết kế hệ thống	6
2.1.1.	Biểu đồ phân cấp chức năng (BFD)	6
2.1.2.	Biểu đồ luồng dữ liệu (DFD)	6
2.2.	Quản trị hệ thống	7
2.3.	Cơ sở dữ liệu	7
2.4.	Ngôn ngữ lập trình	10
Chương III. PHÂN TÍCH THIẾT KẾ HỆ THỐNG	10
3.1.	Phân tích thiết kế CSDL	10
3.2.	Phân tích thiết kế chức năng	12
3.3.	Các chức năng chưa làm được	12
Chương IV. CÀI ĐẶT VÀ HƯỚNG DẪN SỬ DỤNG	12
4.1.	Cài đặt CSDL	12
4.2.	Cài đặt giả lập môi trường server hosting	13
4.3.	Giao diện người dùng (user)	14
4.3.1.	Giao diện trang chủ	14
4.3.2.	Giao diện xem trước công việc	16
4.3.3.	Giao diện đầu trang và cuối trang	17
4.3.4.	Giao diện tìm kiếm công việc	18
4.3.5.	Giao diện trang thông báo người dùng	19
4.3.6.	Giao diện trang đăng nhập	21
4.3.7.	Giao diện trang đăng kí	22
4.3.8.	Giao diện trang quên mật khẩu	22
4.3.9.	Giao diện chi tiết công việc	23
4.3.10.	Giao diện đơn ứng tuyển công việc	24
4.3.11.	Giao diện trang thông tin người dùng	26
4.3.12.	Giao diện thống kê hoạt động người dùng trong 12 tháng gần nhất	31
4.3.13.	Giao diện theo dõi công ty yêu thích	31
4.3.14.	Giao diện trang các đơn ứng tuyển	32
4.3.15.	Giao diện trang theo dõi các công việc yêu thích	34
4.3.16.	Giao diện trang lịch sử công việc đã xem	35
4.3.17.	Giao diện trang thư mời ứng tuyển	36
4.3.18.	Giao diện trang cài đặt thông báo việc làm	36
4.3.19.	Giao diện trang quản lí hóa đơn	37
4.3.20.	Giao diện trang cài đặt	37
4.3.21.	Giao diện trang tìm kiếm công ty	39
4.3.22.	Giao diện trang chủ công ty	40
4.3.23.	Giao diện công việc ở trang chủ công ty	42
4.3.24.	Giao diện dịch vụ	43
4.3.25.	Giao diện ngoài	43
4.3.26.	Giao diện trang 404	44
4.4.	Giao diện quản trị công ty	45
4.4.1.	Giao diện đăng nhập	45
4.4.2.	Giao diện trang thông kê quản trị công ty	45
4.4.3.	Giao diện trang đầu của quản trị công ty	46
4.4.4.	Giao diện quản lý công việc	46
4.4.5.	Giao diện tìm kiếm công việc	48
4.4.6.	Giao diện quản lý đơn ứng tuyển	48
4.4.7.	Giao diện tìm kiếm đơn ứng tuyển	51
4.4.8.	Giao diện cài đặt	52
4.5.	Giao diện quản trị trang web(admin)	53
4.5.1.	Giao diện đăng nhập	53
4.5.2.	Giao diện trang chủ admin	53
4.5.3.	Giao diện quản lí công ty	54
4.5.4.	Giao diện tìm kiếm công ty	55
4.5.5.	Giao diện quản lí hóa đơn	56
4.5.6.	Giao diện tìm kiếm hóa đơn	56
4.5.7.	Giao diện quản lí người dùng	56
4.5.8.	Giao diện tìm kiếm người dùng	57
4.4.10.	Giao diện quản lí công việc	58
4.4.11.	Giao diện tìm kiếm công việc	59
4.6.	Ứng dụng bảo mật	59
4.6.1.	Phân quyền token (đăng nhập) và api backend	59
4.6.2.	Ngăn trặn truy cập api khi chưa có quyền	60
4.6.3.	Mã capcha	61
4.6.4.	Phòng chống tấn công XSS	61
4.7.	Tích hợp cổng thanh toán trực tiếp vào trang web	62
4.8.	Các công nghệ	63
4.8.1.	Đăng nhập bằng facebook, google	63
4.8.2.	Hệ thống gợi ý việc làm phù hợp	64
4.8.3.	Công nghệ websocket làm hệ thống thông báo	68
4.8.4.	Chatbot	71
4.9.  Đẩy dự án lên mạng có tên miền (deloy)	73
Chương V. KẾT LUẬN	76
CHƯƠNG VI. HƯỚNG PHÁT TRIỂN	77
CHƯƠNG VII. TÀI LIỆU THAM KHẢO	77


Chương I. MÔ TẢ, KHẢO SÁT VÀ XÁC ĐỊNH YÊU CẦU TRANG WEB
1.1	Mô tả trang web quản lý, tìm kiếm việc làm
-	Trang web tìm kiếm việc làm là nền tảng kết nối hiệu quả giữa người tìm việc và các nhà tuyển dụng. Với giao diện thân thiện, dễ sử dụng và hệ thống lọc thông minh, người dùng có thể dễ dàng tìm kiếm các công việc phù hợp theo ngành nghề, vị trí, mức lương, kinh nghiệm và địa điểm.
-	Hệ thống cho phép người dùng tạo hồ sơ cá nhân, nộp CV trực tuyến, theo dõi quá trình ứng tuyển, và nhận thông báo tự động khi có công việc phù hợp được đăng tải. Đồng thời, các công ty có thể đăng tuyển dụng, quản lý danh sách ứng viên và tương tác trực tiếp thông qua nền tảng.
-	Bên cạnh đó, trang web còn tích hợp các công nghệ hiện đại như gợi ý việc làm thông minh, chatbot hỗ trợ 24/7, các dịch vụ theo thời gian thực qua WebSocket và bảo mật thông tin tối đa nhằm nâng cao trải nghiệm người dùng...
1.2	Khảo sát, xác định yêu cầu bài toán
1.2.1. Khảo sát hiện trạng
-	Hiện nay, nhu cầu tìm kiếm việc làm trực tuyến ngày càng gia tăng, đặc biệt trong bối cảnh chuyển đổi số đang diễn ra mạnh mẽ tại Việt Nam và trên thế giới. Do đó, cần xây dựng một hệ thống tìm kiếm việc làm có giao diện hiện đại, thân thiện, hỗ trợ người dùng hiệu quả trong quá trình tìm kiếm, ứng tuyển và quản lý thông tin việc làm
1.2.2. Mục tiêu của hệ thống
Hệ thống tìm kiếm việc làm hướng đến mục tiêu:
-	Hỗ trợ người dùng tìm kiếm công việc phù hợp dựa trên các tiêu chí cụ thể (ngành nghề, vị trí, kinh nghiệm, mức lương,...).
-	Cho phép ứng viên đăng ký tài khoản, tạo và cập nhật hồ sơ cá nhân, tải lên CV, và theo dõi trạng thái ứng tuyển.
-	Cung cấp cho nhà tuyển dụng các công cụ đăng tin tuyển dụng, lọc và quản lý danh sách ứng viên.
-	Hỗ trợ gợi ý công việc thông minh dựa trên hồ sơ người dùng thông qua công nghệ AI/NLP.
-	Tích hợp hệ thống thông báo và tương tác thời gian thực (WebSocket) nhằm cải thiện trải nghiệm người dùng.
1.2.3. Phạm vi hệ thống
Hệ thống sẽ bao gồm các chức năng chính sau:
•	Quản lý người dùng: đăng ký, đăng nhập, phân quyền (Người dùng, Công ty tuyển dụng, Quản trị viên).
•	Quản lý hồ sơ cá nhân và công ty.
•	Đăng tin tuyển dụng, tìm kiếm và lọc công việc.
•	Ứng tuyển trực tuyến, theo dõi tiến trình tuyển dụng.
•	Hệ thống thông báo, gợi ý việc làm, và hỗ trợ qua chatbot.
•	Cổng thanh toán quốc tế paypal
Chương II. KIẾN THỨC ÁP DỤNG
2.1.	Phân tích & Thiết kế hệ thống
2.1.1.	Biểu đồ phân cấp chức năng (BFD)
 
Hình  2.1: Biểu đồ BFD
2.1.2.	Biểu đồ luồng dữ liệu (DFD)
 
Hình 2.2: Biểu đồ DFD 
2.2.	Quản trị hệ thống
-	Cơ chế phân quyền người dùng(USER, COMPANY, ADMIN)
-	Giao diện quản trị cho phép kiểm duyệt tin đăng, khóa tài khoản, thống kê người dùng, kiểm tra nhật ký hệ thống:
•	Quản trị công ty: đăng công việc, kiểm duyệt đơn xét tuyển, thông kê hoạt động
•	Quản trị trang(admin): kiểm xoát tài khoản công ty, người dùng, khóa tài khoản, thông kê hoạt động, doanh thu
-	Tích hợp bảo mật JWT, kiểm soát truy cập theo token và vai trò.
-	Cấu trúc log và công cụ giám sát hệ thống (Spring Boot Actuator, logback).
2.3.	Cơ sở dữ liệu
-	Thiết kế cơ sở dữ liệu quan hệ sử dụng MySQL.
-	Một số bảng chính: role, users, companies, jobs, applied_jobs , admin, ....
 
Hình 2.1: Các quan hệ company
 
Hình 2.2: Các quan hệ job và applied_job
 
Hình 2.3: Các quan hệ user và admin
2.4.	Ngôn ngữ lập trình
-	Front-end: Angular 19+ mới nhất, HTML, SCSS, Bootstrap, typescript, ...
-	Back-end: Java (java spring boot 3x)
-	Database: Mysql
-	Hệ thống gợi ý: python (AL/NLP)
-	Hệ thống thông báo: Websocket
-	Devops: docker
Chương III. PHÂN TÍCH THIẾT KẾ HỆ THỐNG
3.1.	Phân tích thiết kế CSDL
-	Người dùng(users):
•	Bảng users: lưu trữ thông tin người dùng và tài khoản(email), mật khẩu(đã mã khóa)
•	Bảng user_payments: lưu trữ thông tin người dùng thanh toán
•	Bảng user_cvs: lưu trữ các file CV (pdf, docx)
•	Bảng users_favourite_companies: lưu trữ người dùng theo dõi công ty 
•	Bảng users_favourite_jobs: lưu trữ người dùng theo dõi công việc
•	Bảng user_dashboards: lưu trữ, thống kê hoạt động người dùng
•	Bảng user_notifications: lưu trữ thông báo người dùng
•	Bảng job_view_history: lưu trữ các công việc mà người dùng đã xem
-	Công ty (companies):
•	Bảng companies: lưu trữ thông tin công ty, tài khoản, mật khẩu(đã mã khóa)
•	Bảng company_images: lưu trữ các ảnh của công ty
•	Bảng company_dashboards: lưu trữ, thống kê hoạt động của công ty
•	Bảng company_feedback_refus: lưu trữ các đơn từ chối của công ty
•	Bảng company_feedback_accept: lưu trữ các đơn đồng ý của công ty
•	Bảng users_favourite_companies: lưu trữ người dùng theo dõi công ty 
-	Công việc (jobs):
•	Bảng jobs: lưu trữ các thông tin của công việc
•	Bảng job_images: lưu trữ các hình ảnh của công việc
•	Bảng job_invitations: lưu trữ các người dùng được mời ứng tuyển công việc
•	Bảng job_functions: lưu trữ các ngành công việc
•	Bảng users_favourite_jobs: lưu trữ người dùng theo dõi công việc
•	Bảng job_view_history: lưu trữ các công việc mà người dùng đã xem
-	Đơn ứng tuyển (applied_jobs):
•	Bảng applied_jobs: lưu trữ thông tin người dùng ứng tuyển công việc
•	Bảng applied_job_cvs: lưu trữ các file CV (pdf, docx,..) của người dùng
-	Quản trị (admin):
•	Bảng admin: lưu trữ tài khoản quản trị viên
•	Bảng admin_dashboard_active: lưu trữ, thông kê hoạt động của trang web
•	Bảng admin_dashboard_revenue: lưu trữ, thông kê doanh thu của trang web
-	Ngoài ra:
•	Bảng roles: lưu trữ quyền của hệ thống
•	Bảng chatbot_questions: lưu trữ câu hỏi, trả lời của chatbot
•	Bảng forgot_tokens: lưu trữ token được cấp cho quá trình quên mật khẩu
•	Bảng password_reset_token: lưu trữ thời hạn token cho mỗi người dùng
•	Bảng districts: lưu trữ các xã, huyện
•	Bảng provinces: lưu trữ các tỉnh, thành phố
3.2.	Phân tích thiết kế chức năng
 
Hình 3.1: Bảng thiết kế chức năng
-	Quản trị viên:
•	Quản lí người dùng: khóa tài khoản
•	Quản lí công ty: khóa tài khoản
•	Hỗ trợ chatbot: hỗ trợ người dùng
-	Công ty:
•	Quản lí công việc: tạo, xóa, sửa việc làm
•	Quản lí đơn ứng tuyển: chấp nhận, từ chối ứng viên
•	Thông báo: thông báo việc làm hết hạn, duyệt đơn ứng tuyển, ...
-	Người dùng:
•	Quản lí hồ sơ: thiết lập hồ sơ cá nhân và ứng tuyển các công việc
•	Tìm kiếm việc làm: tìm kiếm việc làm trên web qua các công ty
•	Thanh toán: thanh toán hóa đơn lập công ty, ...
3.3.	Các chức năng chưa làm được
-	Thanh toán xong chưa tạo được tài khoản công ty được ngay
Chương IV. CÀI ĐẶT VÀ HƯỚNG DẪN SỬ DỤNG
4.1.	Cài đặt CSDL
-	Bước 1: Tải MySQL Installer
•	Truy cập: https://dev.mysql.com/downloads/installer/
•	Chọn:
•	MySQL Installer for Windows (x86, 32-bit): Có 2 bản:
•	Web (chỉ 2.5MB) – tải về online khi cài đặt.
•	Full (khoảng 400MB) – chứa sẵn tất cả thành phần.
•	Nhấn Download, sau đó chọn:  “No thanks, just start my download” để tải nhanh.
________________________________________
-	Bước 2: Cài đặt MySQL
•	Chạy file .msi vừa tải về.
•	Chọn kiểu cài đặt:
•	Developer Default: Khuyến nghị (gồm MySQL Server, Workbench, Shell, Docs…).
•	Hoặc chọn Custom nếu bạn muốn lựa chọn thủ công.
•	Nhấn Next liên tục → Install → Chờ cài đặt hoàn tất.
-	Bước 3: Thiết lập cấu hình
•	MySQL Server Configuration:
•	Port: để mặc định là 3306.
•	Authentication Method: chọn Use Legacy Authentication nếu bạn dùng với các app cũ.
•	Tạo mật khẩu cho user root và có thể tạo thêm user nếu muốn.
•	Nhấn Execute để hoàn tất cấu hình.
-	Bước 4: Tạo bảng SQL
-	Muốn lấy dữ liệu sẵn có bảng thì chỉ cần chạy docker có sẵn trong dự án git
•	Chạy lệnh docker compose up -d
4.2.	Cài đặt giả lập môi trường server hosting
-	Cài node.js:
•	Kiểm tra: node –v, npm –v
-	Cài angular: 
•	npm install -g @angular/cli
•	ng version
-	Tạo dự án angular:
•	ng new ten-du-an
-	Cài các thư viện:
•	npm -i
-	Chạy ứng dụng:
•	yarn start
 
Hình 4.1: mở cổng localhost
4.3.	Giao diện người dùng (user)
4.3.1.	Giao diện trang chủ
 <img width="975" height="448" alt="image" src="https://github.com/user-attachments/assets/468f0cec-7c3b-4cf0-b64a-76df14093965" />

Hình 4.2: trang chủ 
 <img width="975" height="384" alt="image" src="https://github.com/user-attachments/assets/88aa6aee-743b-4a90-97ae-cbf8fa755921" />

Hình 4.3: trang chủ
 <img width="975" height="439" alt="image" src="https://github.com/user-attachments/assets/0effbba2-12ae-41ec-a5d8-4f3231c92135" />

Hình 4.4: trang chủ
 <img width="975" height="372" alt="image" src="https://github.com/user-attachments/assets/05c04b8b-bc5b-4d73-ab75-ee3560f0166f" />

Hình 4.5: trang chủ
 <img width="975" height="439" alt="image" src="https://github.com/user-attachments/assets/78597f0c-1a69-497a-b0df-8be241ca69ec" />

Hình 4.6: trang chủ
 <img width="975" height="449" alt="image" src="https://github.com/user-attachments/assets/80c071d3-279a-4aed-8e47-f1bd8b393d3b" />
 
Hình 4.7: trang chủ
4.3.2.	Giao diện xem trước công việc
 <img width="975" height="417" alt="image" src="https://github.com/user-attachments/assets/445f4ca2-9a56-48fb-a527-3422fcedb13b" />

Hình 4.8: Giao diện xem trước công việc
4.3.3.	Giao diện đầu trang và cuối trang
 <img width="975" height="108" alt="image" src="https://github.com/user-attachments/assets/60c7fe60-52aa-4847-95d6-2abe2249093d" />

Hình 4.9: Giao diện đầu trang thông tin người dùng trước đăng nhập
 <img width="975" height="245" alt="image" src="https://github.com/user-attachments/assets/57764c54-b1d4-4881-87e2-4fb82ff39f58" />

Hình 4.10: Giao diện đầu trang thông tin người dùng sau đăng nhập
 <img width="975" height="208" alt="image" src="https://github.com/user-attachments/assets/52d9924c-8d5f-4d9f-94a4-b0659378cf9d" />

Hình 4.11: Giao diện cuối trang người dùng
4.3.4.	Giao diện tìm kiếm công việc
 <img width="975" height="449" alt="image" src="https://github.com/user-attachments/assets/13c8aab8-886b-443a-9b0c-36589a86703b" />

Hình 4.12: Giao diện tìm kiếm công việc theo từ khóa, lọc, ngày đăng
 <img width="975" height="447" alt="image" src="https://github.com/user-attachments/assets/f760a9b6-df11-47dd-a61c-e45ae86cf8bd" />

Hình 4.13: Giao diện tìm kiếm công việc sắp xếp theo lương giảm, tăng
 <img width="975" height="441" alt="image" src="https://github.com/user-attachments/assets/c1ab00fe-ad1d-4050-8ee9-a3a743e2323d" />

Hình 4.14: Giao diện tìm kiếm công việc sắp xếp theo view giảm, tăng
 <img width="975" height="448" alt="image" src="https://github.com/user-attachments/assets/8a09a18d-7aa5-4436-b29a-06fa90a4d391" />

Hình 4.15: Giao diện tìm kiếm công việc sắp xếp theo tên giảm, tăng
4.3.5.	Giao diện trang thông báo người dùng
 <img width="975" height="451" alt="image" src="https://github.com/user-attachments/assets/572db398-0a79-4afb-a23d-fc2d11463b95" />

Hình 4.16: Giao diện thông báo người dùng
 <img width="975" height="441" alt="image" src="https://github.com/user-attachments/assets/10877048-32de-4c0a-9de0-b19cc4eb7e0f" />

Hình 4.17: Giao diện thông báo người dùng
 <img width="975" height="443" alt="image" src="https://github.com/user-attachments/assets/6fc58096-b9e2-47d3-970e-b0020ac5868e" />

Hình 4.18: Giao diện thông báo người dùng
4.3.6.	Giao diện trang đăng nhập
 
Hình 4.19: Giao diện đăng nhập người dùng





4.3.7.	Giao diện trang đăng kí
 
Hình 4.20: Giao diện đăng kí người dùng
4.3.8.	Giao diện trang quên mật khẩu
 
Hình 4.21: Giao diện nhập email quên mật khẩu
 
Hình 4.22: Email gửi link xác nhận đổi mật khẩu
 
Hình 4.23: Giao diện trang đổi mật khẩu mới
4.3.9.	Giao diện chi tiết công việc
 
Hình 4.24: Giao diện trang chi tiết công việc còn hạn
 
Hình 4.25: Giao diện trang chi tiết công việc hết hạn hoặc đóng sớm
4.3.10.	Giao diện đơn ứng tuyển công việc
 
Hình 4.26: Giao diện đơn ứng tuyển công việc
 
Hình 4.27: Giao diện đơn ứng tuyển công việc
 
Hình 4.28: Giao diện khi thành công ứng tuyển
 
Hình 4.29: Giao diện sau khi ứng tuyển
 
Hình 4.30: Giao diện xem đơn đã ứng tuyển
4.3.11.	Giao diện trang thông tin người dùng
 
Hình 4.31: Giao diện thông tin người dùng
 
Hình 4.32: Giao diện kho hồ sơ CV người dùng
 
Hình 4.33: Giao diện thay đổi thông tin người dùng
 
Hình 4.34: Giao diện sau khi thay đổi thông tin người dùng
 
Hình 4.35: Giao diện tải file CV lên trang web
 
Hình 4.36: Giao diện tải file CV lên trang web thành công
 
Hình 4.37: Giao diện sau khi tải file CV lên trang web thành công
 
Hình 4.38: Có thể xem trực tiếp file CV trên trang web và tải về
 
Hình 4.39: Xóa, đổi tên file CV
4.3.12.	Giao diện thống kê hoạt động người dùng trong 12 tháng gần nhất
 
Hình 4.40: Giao diện thống kê hoạt động người dùng
4.3.13.	Giao diện theo dõi công ty yêu thích
 
Hình 4.41: Giao diện trang theo dõi các công ty yêu thích
4.3.14.	Giao diện trang các đơn ứng tuyển
 
Hình 4.42: Giao diện trang các đơn người dùng ứng tuyển
 
Hình 4.43: Giao diện xem đơn đã ứng tuyển
 
Hình 4.44: Giao diện xem đơn đã được duyệt
 
Hình 4.45: Giao diện xem đơn đã được duyệt
4.3.15.	Giao diện trang theo dõi các công việc yêu thích
-	Sắp xếp theo thời gian theo dõi
-	Không hiện các công việc đã hết hạn hoặc đóng sớm
 
Hình 4.46: Giao diện trang theo dõi các công việc yêu thích
 
Hình 4.47: Bỏ theo dõi công việc
 
Hình 4.48: Sau khi bỏ theo dõi công việc
4.3.16.	Giao diện trang lịch sử công việc đã xem
-	Cập nhật và sắp xếp theo thời gian xem
-	Sẽ không hiện các công việc đã hết hạn hoặc đóng sớm

 
Hình 4.49: Giao diện lịch sử công việc đã xem
4.3.17.	Giao diện trang thư mời ứng tuyển
 
Hình 4.50: Giao diện lịch sử công việc đã xem
4.3.18.	Giao diện trang cài đặt thông báo việc làm
 
Hình 4.51: Giao diện thông báo việc làm
4.3.19.	Giao diện trang quản lí hóa đơn
 
Hình 4.52: Giao diện quản lí hóa đơn
4.3.20.	Giao diện trang cài đặt
 
Hình 4.53: Giao diện trang cài đặt tài khoản
 
Hình 4.54: Giao diện thay đổi email 
 
Hình 4.55: Giao diện thay đổi mật khẩu
4.3.21.	Giao diện trang tìm kiếm công ty
 
Hình 4.56: Giao diện trang tìm kiếm công ty
 
Hình 4.57: Giao diện trang tìm kiếm công ty
 
Hình 4.58: Theo dõi công ty
4.3.22.	Giao diện trang chủ công ty 
 
Hình 4.59: Giao diện trang chủ công ty
 
Hình 4.60: Giao diện trang chủ công ty
 
Hình 4.61: Giao diện trang chủ công ty
4.3.23.	Giao diện công việc ở trang chủ công ty 
 
Hình 4.62: Giao diện công việc ở trang chủ công ty
 
Hình 4.63: Giao diện tìm kiếm công việc ở trang chủ công ty
4.3.24.	Giao diện dịch vụ
 
Hình 4.64: Giao diện dịch vụ
4.3.25.	Giao diện ngoài 
 
Hình 4.65: Giao diện ngoài
 
Hình 4.66: Giao diện ngoài
4.3.26.	Giao diện trang 404
 
Hình 4.67: Giao diện trang 404
4.4.	Giao diện quản trị công ty
4.4.1.	Giao diện đăng nhập
 
Hình 4.68: Giao diện đăng nhập của công ty
4.4.2.	Giao diện trang thông kê quản trị công ty
 
Hình 4.69: Giao diện trang chủ của công ty
4.4.3.	Giao diện trang đầu của quản trị công ty
 
Hình 4.70: Giao diện trang đầu của công ty
4.4.4.	Giao diện quản lý công việc
 
Hình 4.71: Giao diện quản lý công việc
 
Hình 4.72: Giao diện thêm công việc mới
 
Hình 4.73: Chỉnh sửa, cập nhật công việc
 
Hình 4.74: Đóng công việc
4.4.5.	Giao diện tìm kiếm công việc
 
Hình 4.75: Giao diện tìm kiếm công việc
4.4.6.	Giao diện quản lý đơn ứng tuyển
 
Hình 4.76: Giao diện quản lí đơn ứng tuyển
 
Hình 4.77: Xem chi tiết đơn ứng tuyển
 
Hình 4.78: Giao diện đơn chấp nhận
 
Hình 4.79: Giao diện đơn từ chối
 
Hình 4.80: Giao diện đơn sau khi duyệt
 
Hình 4.81: Giao diện đơn từ chối
 
Hình 4.82: Giao diện đơn chấp nhận
4.4.7.	Giao diện tìm kiếm đơn ứng tuyển
 
Hình 4.83: Giao diện tìm kiếm đơn ứng tuyển
4.4.8.	Giao diện cài đặt
 
Hình 4.84: Giao diện cài đặt
4.4.9.	Giao diện trang công ty góc nhìn từ công ty
 
Hình 4.85: Giao diện trang chủ công ty
4.5.	Giao diện quản trị trang web(admin)
4.5.1.	Giao diện đăng nhập
 
Hình 4.86: Giao diện đăng nhập admin
4.5.2.	Giao diện trang chủ admin
 
Hình 4.87: Giao diện trang chủ admin
 
Hình 4.88: Giao diện trang chủ admin
4.5.3.	Giao diện quản lí công ty
 
Hình 4.89: Giao diện trang quản lý công ty
 
Hình 4.90: Khóa công ty
4.5.4.	Giao diện tìm kiếm công ty
 
Hình 4.91: Tìm kiếm công ty
4.5.5.	Giao diện quản lí hóa đơn
 
Hình 4.92: Giao diện quản lí hóa đơn
4.5.6.	Giao diện tìm kiếm hóa đơn
 
Hình 4.93: Giao diện tìm kiếm hóa đơn
4.5.7.	Giao diện quản lí người dùng
 
Hình 4.94: Giao diện quản lí người dùng
 
Hình 4.95: khóa tài khoản người dùng
4.5.8.	Giao diện tìm kiếm người dùng
 
Hình 4.96: Tìm kiếm người dùng
4.4.10.	Giao diện quản lí công việc
 
Hình 4.97: Giao diện quản lí công việc
 
Hình 4.98: đóng công việc
4.4.11.	Giao diện tìm kiếm công việc
 
Hình 4.99: tìm kiếm công việc
4.6.	Ứng dụng bảo mật
4.6.1.	Phân quyền token (đăng nhập) và api backend
-	Chia ra 3 quyền: USER, COMPANY, ADMIN
-	Mỗi quyền sẽ có 1 token đăng nhập khác nhau
 
Hình 4.100:3 loại token USER, COMPANY, ADMIN
-	Phân quyền api:
•	Phân quyền trên 1 đoạn api
 
Hình 4.101: phân quyền từng đoạn api
•	Phân quyền trên từng api một
 
Hình 4.102: phân quyền từng api một
4.6.2.	Ngăn trặn truy cập api khi chưa có quyền
-	Nếu không có token đăng nhập trang company, admin sẽ dẫn đến trang 404 và báo lỗi
 
Hình 4.103: ngăn chặn truy cập trực tiếp vào trang chủ quản trị
4.6.3.	Mã capcha
-	Sử dụng mã capcha để tránh dò mật khẩu (vét cạn)
 
Hình 4.104: mã capcha
4.6.4.	Phòng chống tấn công XSS
 
Hình 4.105: Phòng chống XSS
4.7.	Tích hợp cổng thanh toán trực tiếp vào trang web
 
Hình 4.106: cổng thanh toán quốc tế PAYPAL
 
Hình 4.107: Khi thanh toán thành công
4.8.	Các công nghệ
4.8.1.	Đăng nhập bằng facebook, google
-	Gọi api yêu cầu đăng nhập
-	Đăng nhập và gọi api lấy dữ liệu về (gmail, avatar, tên, số điện thoại)
 
Hình 4.108: Đăng nhập bằng facebook
 
Hình 4.109: Đăng nhập bằng google
4.8.2.	Hệ thống gợi ý việc làm phù hợp
-	Bước 1: kết nối database và lấy danh sách công việc trong python:
 
Hình 4.110: Kết nối và lấy dữ liệu từ database trong python
-	Bước 2: chuẩn bị class để hứng dữ liệu cần phân tích
•	Phân tích dựa trên công việc tưởng ứng
•	Phân tích dựa trên thông tin cá nhân
 
Hình 4.111: Class hứng dữ liệu
-	Bước 3: Xử lí dữ liệu
•	Sử dung thư viện NLP: TfidfVectorizer() sử dụng để chuyển đổi văn bản (text) thành dạng vector số bằng cách tính toán giá trị TF-IDF cho từng từ trong tập văn bản
	TF (Term Frequency): Tần suất xuất hiện của một từ trong một tài liệu.
	DF (Inverse Document Frequency): Đo độ quan trọng của từ dựa trên số lượng tài liệu có chứa từ đó. Từ phổ biến quá (như "the", "is") sẽ có giá trị thấp
•	Sử dụng thư viện cosine_similarity là một hàm dùng để tính độ tương đồng giữa hai vector dựa trên cosine của góc giữa chúng. Hàm này thường được dùng trong xử lý ngôn ngữ tự nhiên (NLP) để đo mức độ giống nhau giữa các tài liệu sau khi chúng được chuyển thành vector
•	Sau đó, ta có 1 dãy các số biểu diễn cho độ tưởng thích, sắp xếp và lấy 5 công việc có điểm cao nhất
 
Hình 4.112: Xử lí dữ liệu lấy top 5 công việc đề xuất 
 
Hình 4.113: Xử lí dữ liệu lấy top 5 công việc đề xuất người dùng
-	Bước 4: Tạo cổng api để kết nối dữ liệu với backend
•	Sử dụng thư viện fastapi và uvicorn
 
Hình 4.90: Tạo cổng api
 
Hình 4.114: Đề xuất 5 công việc giống với công việc đang xem
 
Hình 4.115: Đề xuất 5 công việc với thông tin người dùng
4.8.3.	Công nghệ websocket làm hệ thống thông báo
-	WebSocket là một giao thức truyền thông (communication protocol) cho phép giao tiếp hai chiều (full-duplex) giữa client (trình duyệt hoặc app) và server thông qua một kết nối TCP duy nhất, mà không cần phải tạo nhiều request như HTTP thông thường.
-	WebSocket giúp client và server có thể "nói chuyện" với nhau liên tục, theo thời gian thực, mà không cần client phải liên tục gửi request để "hỏi thăm" server
-	Ứng dụng: làm hệ thống thông báo, nhắn tin, ...
-	Bước 1: Cấu hình bên backend
 
Hình 4.116: Cấu hình socket backend
-	Bước 2: Kết nối font-end với server /ws
 
Hình 4.117: Thiết lập kết nối với /ws
-	Bước 3: Giao tiếp
 
Hình 4.118: Công ty cập nhật công việc
 
Hình 4.119: Bên người dùng sẽ tự động được thông báo theo thời gian thực
4.8.4.	Chatbot
-	Bước 1: thiết kế trong database
 
Hình 4.120: bảng chatbot trong database
-	Bước 2: cấu hình trong backend
 
Hình 4.121: cấu hình trong backend
-	Bước 3: cấu hình trên font-end và hiện giao diện:
 
Hình 4.122: chatbot
4.9.  Đẩy dự án lên mạng có tên miền (deloy)
-	Bước 1: Đẩy database lên railway
 
Hình 4.123: mysql trên railway
-	Bước 2: kết nối database trên raiway với backend
 
Hình 4.124: cài đặt kết nối trên application.yml
-	Bước 3: Cài đặt dockerfile để nén cài đặt backend
 
Hình 4.125: cài đặt dockerfile
-	Bước 4: Deloy lên server web render
 
Hình 4.126: tải lên backend lên server thành công
-	Bước 5: Thành công
 
Hình 4.127: web sau khi deloy
Chương V. KẾT LUẬN
-	Website tìm kiếm việc làm được xây dựng với mục tiêu tạo ra một nền tảng trung gian kết nối giữa ứng viên đang tìm kiếm cơ hội việc làm và doanh nghiệp có nhu cầu tuyển dụng nhân sự. Hệ thống hỗ trợ đầy đủ các chức năng cần thiết như: đăng tin tuyển dụng, nộp đơn ứng tuyển, quản lý công việc, gợi ý việc làm phù hợp, và quản trị hệ thống.
-	Quá trình xây dựng hệ thống đã trải qua các giai đoạn: khảo sát, phân tích yêu cầu, thiết kế hệ thống, thiết kế cơ sở dữ liệu, xây dựng sơ đồ chức năng và luồng dữ liệu, từ đó đảm bảo hệ thống hoạt động logic, hiệu quả và có khả năng mở rộng về sau. Việc phân chia rõ ràng giữa vai trò công ty, ứng viên và quản trị viên giúp hệ thống dễ quản lý và đảm bảo tính bảo mật, phân quyền.
-	Với giao diện thân thiện, trực quan cùng với nền tảng kỹ thuật hiện đại (có thể triển khai bằng Angular, Spring Boot, MySQL…), hệ thống hướng đến trải nghiệm người dùng tốt, tăng tính tương tác giữa nhà tuyển dụng và người tìm việc. Ngoài ra, hệ thống còn có khả năng tích hợp các chức năng nâng cao như gợi ý việc làm bằng trí tuệ nhân tạo (AI), phân tích xu hướng tuyển dụng, hoặc chatbot hỗ trợ.
-	Kết luận, hệ thống tìm kiếm việc làm không chỉ mang lại lợi ích thiết thực cho người lao động và doanh nghiệp mà còn góp phần giải quyết bài toán việc làm trong xã hội theo hướng hiện đại, linh hoạt và dễ tiếp cận.
CHƯƠNG VI. HƯỚNG PHÁT TRIỂN
-	Cải thiện hệ thống gợi ý việc làm thông minh dựa trên hồ sơ ứng viên, lịch sử ứng tuyển và hành vi tìm kiếm
-	Cải thiện trải nghiệm người dùng (UX/UI)
CHƯƠNG VII. TÀI LIỆU THAM KHẢO

