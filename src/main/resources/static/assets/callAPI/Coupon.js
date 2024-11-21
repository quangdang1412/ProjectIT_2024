document.querySelector(".apply-btn").addEventListener("click", function (e) {
  e.preventDefault();
  const couponValue = document.querySelector(".coupon-input").value;
  if (couponValue) {
    $.ajax({
      url: `/api/user/checkCoupon/${couponValue}`,
      type: "GET",
      dataType: "json",
      success: function (data) {
        if (data.active === 0) {
          Swal.fire({
            icon: "error",
            title: "Mã giảm giá đã hết hạn!",
            text: "Mã giảm giá này không còn hiệu lực. Vui lòng kiểm tra lại.",
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
          });
        } else if (data && data.percentage) {
          const discount = data.percentage;
          const message = `Áp dụng mã giảm giá thành công: -${discount}%`;
          Swal.fire({
            icon: "success",
            title: "Áp dụng mã giảm giá thành công!",
            text: message,
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
          });

          const totalAmountElement = document.getElementById("totalAmount");
          const totalAmount = parseInt(
            totalAmountElement.textContent.replace(/[^0-9.-]+/g, "")
          );
          const shippingCostElement = document.getElementById("shippingCost");
          const shippingCost = parseInt(
            shippingCostElement.textContent.replace(/[^0-9.-]+/g, "")
          );
          const discountAmount = (totalAmount * discount) / 100;
          const total = totalAmount - discountAmount + shippingCost;
          document.getElementById("totalCoupon").textContent =
            formatCurrency(discountAmount) + " VND";
          document.getElementById("totalElement").textContent =
            formatCurrency(total) + " VND";
        } else {
          Swal.fire({
            icon: "error",
            title: "Mã giảm giá không hợp lệ!",
            text: "Vui lòng kiểm tra lại mã giảm giá của bạn.",
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
          });
        }
      },
      error: function (xhr, status, error) {
        console.error("Lỗi khi kiểm tra mã giảm giá:", error);
        Swal.fire({
          icon: "error",
          title: "Lỗi hệ thống!",
          text: "Không thể kiểm tra mã giảm giá. Vui lòng thử lại sau.",
          toast: true,
          position: "top-end",
          showConfirmButton: false,
          timer: 3000,
          timerProgressBar: true,
        });
      },
    });
  } else {
    Swal.fire({
      icon: "error",
      title: "Vui lòng nhập mã giảm giá!",
      text: "Vui lòng nhập mã giảm giá của bạn.",
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
    });
  }
});
function formatCurrency(amount) {
  return amount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
