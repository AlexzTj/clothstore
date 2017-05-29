var cartApp = angular.module("cartApp",[]);

cartApp.controller("cartCtrl",function($scope,$http){
    $scope.isCheckoutDisabled=true;
    $scope.itemsSize = function(){
        var size = 0;
        if(angular.isDefined($scope.cart)) {size =  Object.keys($scope.cart.cartItems).length;}
       return size;
    };
    $scope.refreshCart = function(){
        $http.get("/rest/cart").then(function(data){
            $scope.cart=data.data;
            $scope.isCheckoutDisabled = Object.keys(data.data.cartItems).length < 1;
        });

    };
    function genUpdateQtyJsonRequest(){
        var json=[];
        angular.forEach($scope.cart.cartItems, function(value, key) {
            this.push(
                {
                    productId : value.product.id,
                    quantity : value.quantity
                }
                );
        }, json);
        return json;
    }
    $scope.updateCart = function(){

        if($scope.itemsSize() > 0){
            var json = genUpdateQtyJsonRequest();
            var config = {
                headers : {
                    'Content-Type': 'application/json; charset=utf-8'
                }
            };
            $http.put("/rest/cart",JSON.stringify(json),config).then(function(data){
               $scope.refreshCart();
            });
        }
    };

    $scope.deleteCart = function(){
        $http.delete("/rest/cart").then($scope.refreshCart());
    };

    $scope.addToCart = function(productId){
        $http.put("/rest/cart/add/"+productId).then(function(data){
            $scope.refreshCart();
            alert("product added");
        });
    };

    $scope.removeFromCart = function(productId){
        $http.put("/rest/cart/remove/"+productId).then(function(data){
             $scope.refreshCart();
            alert("product removed");
        });
    };

});
