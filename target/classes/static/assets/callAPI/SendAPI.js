function getUserData() {
    return {
        userID: getValue('UserID'),
        userName: getValue('UserName'),
        password: getValue('Password'),
        email: getValue('Email'),
        phone: getValue('Phone'),
        gender: getValue('Gender', 'select'),
        address: getValue('Address'),
        type: getValue('Type', 'select')
    };
}
function getOrderData() {
    return {
        orderID: getValue('OrderID'),
        sellerID: getValue('SellerID', 'select'),
        shipperID: getValue('ShipperID', 'select'),
        orderDate: getValue('OrderDate'),
        deliveryTime: getValue('DeliveryTime'),
        name: getValue('Name'),
        phone: getValue('Phone'),
        address: getValue('Address'),
        totalPrice: getValue('TotalPrice'),
        transportFee: getValue('TransportFee'),
        paymentMethod: getValue('paymentMethod'),
        paymentStatus: getValue('paymentStatus', 'select'),
        status: getValue('Status', 'select')
    };
}
function getPlaceOrderData() {
    return {
        orderID: getValue('OrderID'),
        name: getValue('Name'),
        phone: getValue('Phone'),
        address: `${getValue('Address')}, ${getDropdownValue('communeDropdown')}, ${getDropdownValue('districtDropdown')}, ${getDropdownValue('provinceDropdown')}`,
        shippingOption: getRadioValue('shippingOption'),
        paymentMethod: getRadioValue('paymentMethod'),
        totalAmount: document.getElementById('totalAmount').textContent,
        shippingCost: document.getElementById('shippingCost').textContent,
        finalTotal: document.getElementById('totalElement').textContent
    };
}
function getSupplierData() {
    return {
        supplierID: getValue('SupplierID'),
        supplierName: getValue('SupplierName'),
        email: getValue('Email'),
        phone: getValue('Phone'),
        address: getValue('Address'),
    };
}
function getValue(name, type = 'input') {
    const element = document.querySelector(`${type}[name="${name}"]`);
    return element ? element.value : '   ';
}

function getDropdownValue(dropdownID) {
    const dropdown = document.getElementById(dropdownID);
    return dropdown.options[dropdown.selectedIndex].value;
}

function getRadioValue(radioName) {
    const selected = document.querySelector(`input[name="${radioName}"]:checked`);
    return selected ? selected.value : null;
}
function notify(xhr,url,data) {
    let response = JSON.parse(xhr.responseText);
    if (xhr.readyState === 4)
    {
        if (xhr.status === 200)
        {
            if (response.message === "Success") {
                Swal.fire({
                    icon: "success",
                    title: "Thông báo.",
                    text: response.message,
                }).then(() => {
                    if(url !== "delete")
                        window.location.href = url;
                });
                if(url === "delete")
                    $(`tr:has(a[onclick*='${data}'])`).remove();
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Thông báo.",
                    text: response.message || "Thất bại",
                });
            }
        } else {
            Swal.fire({
                icon: "error",
                title: "Thông báo.",
                text: response.message,
            });
        }
    }
}
function sendRequest(method,endpoint1,endpoint2,data,url){
    let xhr = new XMLHttpRequest();
    xhr.open(method, `http://localhost:8080/api/${endpoint1}/${endpoint2}`, true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        notify(xhr,url,data)
    };
    xhr.send(JSON.stringify(data));
}