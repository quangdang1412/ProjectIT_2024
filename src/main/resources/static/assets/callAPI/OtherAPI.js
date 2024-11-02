function sendOtherData(check,action) {
    let other;
    let method;
    let endpoint;
    let url;
    if(check === '2')
    {
        other = {
            categoryID: getValue("CategoryID"),
            categoryName: getValue("CategoryName")
        }
        method = action === 'update' ? 'PUT':'POST';
        endpoint = action === 'update' ? 'updateCategory':'addCategory';
        url = `/admin/Other?type=${check}`;

    }
    else if(check === '3'){
        other = {
            brandID: getValue("BrandID"),
            brandName: getValue("BrandName")
        }
        method = action === 'update' ? 'PUT':'POST';
        endpoint = action === 'update' ? 'updateBrand':'addBrand';
        url = `/admin/Other?type=${check}`;
    }
    else if(check === '4'){
        other = {
            couponID: getValue("CouponID"),
            percentage: getValue("Percentage"),
        }
        method = action === 'update' ? 'PUT':'POST';
        endpoint = action === 'update' ? 'updateCoupon':'addCoupon';
        url = `/admin/Other?type=${check}`;
    }
    else{
        other = {
            discountID: getValue("DiscountID"),
            percentage: getValue("Percentage"),
            startDate: getValue("StartDate"),
            endDate: getValue("EndDate")
        }
        method ='POST';
        endpoint ='addDiscount';
        url = `/admin/Other?type=${check}`;
    }
    console.log(other + method + endpoint + url)
    sendRequest(method,'other',endpoint, other, url);
}