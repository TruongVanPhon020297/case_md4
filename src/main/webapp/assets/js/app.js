class App {
    static DOMAIN = location.origin;

    static BASER_URL = this.DOMAIN + "/api/";

    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
    }

    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topLeft',
                message: m,
            });
        }
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                position: 'topLeft',
                message: m,
            });
        }
    }

    static formatNumber() {
        $(".num-space").number(true, 0, ',', ' ');
        $(".num-point").number(true, 0, ',', '.');
        $(".num-comma").number(true, 0, ',', ',');
    }

    static formatNumberSpace(x) {
        if (x == null) {
            return x;
        }
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class User {
    constructor(id, fullName, email,password, phone,role, locationRegion) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.locationRegion = locationRegion;
    }
}

class Role {
    constructor(id = 1,name = "USER") {
        this.id = id;
        this.name = name;
    }
}

class Product {
    constructor(id,title,price,quantity,urlImage,category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.urlImage = urlImage;
        this.category = category;
    }
}
class Category {
    constructor(id,title) {
        this.id = id;
        this.title = title
    }
}
class Cart {
    constructor(userId,productId,quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}

class CartInfo {
    constructor(id,grandTotal,user) {
        this.id = id;
        this.grandTotal = grandTotal;
        this.user = user;
    }
}
class CartItem {
    constructor(id,price,quantity,title,totalPrice,product,cart) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.totalPrice = totalPrice;
        this.product = product;
        this.cart = cart;
    }
}
class Order {
    constructor(userId,locationRegion,deliveryDate) {
        this.userId = userId;
        this.locationRegion = locationRegion;
        this.deliveryDate = deliveryDate;
    }
}
class Search {
    constructor(valueUp,valueDown,keySearch) {
        this.valueUp = valueUp;
        this.valueDown = valueDown;
        this.keySearch = keySearch;
    }
}