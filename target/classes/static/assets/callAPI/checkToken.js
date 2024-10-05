export function isTokenExpired(token) {
  if (token == null) {
    return true;
  }

  try {
    const payload = token.split(".")[1];

    const decodedPayload = JSON.parse(
      atob(payload.replace(/-/g, "+").replace(/_/g, "/"))
    );

    const currentTime = Date.now() / 1000; // Đổi sang giây
    return decodedPayload.exp < currentTime;
  } catch (error) {
    console.error("Token không hợp lệ:", error);
    return true;
  }
}
