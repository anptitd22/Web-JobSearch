package com.project.webIT.models.Status;

import java.util.Map;

public class Payment {
    public static final Map<String, String> STATUS_MAP = Map.of(
            "COMPLETED", "Thành công",
            "APPROVED", "Đã duyệt",
            "FAILED", "Thất bại",
            "CANCELED", "Đã hủy"
    );
}
