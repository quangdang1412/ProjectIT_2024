if (isLoggedIn === 1) {
    $.ajax({
        url: "/api/auth/get-token",
        method: "GET",
        success: function(response) {
            var token = response.token;
            if (token) {
                localStorage.setItem('token', token);
                console.log("Token đã được lưu:", token);
            } else {
                console.error("Không tìm thấy token.");
            }
        },
        error: function(error) {
            console.error("Lỗi khi lấy token từ session:", error);
        }
    });
}