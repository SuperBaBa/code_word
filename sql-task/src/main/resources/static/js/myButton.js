(function (win) {
    const myButton = this.elm;

    function MyButton(elm) {
        this.elm = elm;
    }

    MyButton.prototype.addEventListener = function (type, handler) {
        if (this.elm.addEventListener) {
            this.elm.addEventListener(type, handler, false);
        } else {
            this.elm["on" + type] = handler;
        }
    };
    MyButton.prototype.disable = function () {
        this.elm.classList.add("disabled");
    };
    MyButton.prototype.enable = function () {
        this.elm.classList.remove("disabled");
    };
    win.MyButton = MyButton;
}(window));