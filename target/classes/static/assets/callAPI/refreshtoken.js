// Đặt khoảng thời gian gia hạn token - 15 phút
setInterval(() => {
  extendToken();
}, 600000); // 15 phút 900000

function extendToken() {
  const currentToken = localStorage.getItem("token");

  if (!currentToken) {
    console.warn("Không có token hiện tại.");
    return;
  }

  fetch("/api/auth/refresh", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + currentToken,
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.error("Không thể gia hạn token.", response);
        throw new Error("Không thể gia hạn token.");
      }
    })
    .then((data) => {
      // Lưu lại token mới
      localStorage.setItem("token", data.token);
      console.log("Token đã được gia hạn thành công.");
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("Có lỗi khi gia hạn token!");
    });
}
