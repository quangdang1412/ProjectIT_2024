
function addToCart(productId) {
    let quantityElement = document.getElementById('quantityInput');
    let quantity = quantityElement ? quantityElement.value : 1;
    console.log(quantity);
    $.ajax({
        url: '/api/addtocart/' + productId+'/'+quantity,
        type: 'POST',
        success: function(response) {
            console.log("Server response:", response); // Debugging log
            if (response.includes("Đã cập nhật sản phẩm vào giỏ hàng")) {
                Swal.fire({
                    icon: 'success',
                    title: 'Thông báo.',
                    text: response
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Thông báo.',
                    text: response
                });
            }
        }

    });
}
function deleteProinCart(productId) {
    $.ajax({
        url: '/api/deleteproduct/' + productId,
        type: 'DELETE',
        success: function(response) {
            console.log("Server response:", response); // Debugging log
            // Assume the response is a string that indicates success or error
            if (response.includes("Đã xóa sản phẩm khỏi giỏ hàng")) {
                Swal.fire({
                    icon: 'success',
                    title: 'Thông báo.',
                    text: response
                });
                // Remove the table row
                $(`tr:has(a[onclick*='${productId}'])`).remove();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Thông báo.',
                    text: response
                });
            }
        }

    });
}
function deletePro(productId) {
    $.ajax({
        url: '/admin/deleteProduct/' + productId,
        type: 'Put',
        success: function(response) {
            console.log("Server response:", response); // Debugging log
            if (response.includes("Đã xóa sản phẩm")) {
                Swal.fire({
                    icon: 'success',
                    title: 'Thông báo.',
                    text: response
                });
                // Remove the table row
                $(`tr:has(a[onclick*='${productId}'])`).remove();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Thông báo.',
                    text: response
                });
            }
        }

    });
}