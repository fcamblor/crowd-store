define([], function(){
    return {
        //Test if a str is a JSON
        isJSON: function(str) {
            if (this.blank(str)) return false;
            str = str.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@');
            str = str.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']');
            str = str.replace(/(?:^|:|,)(?:\s*\[)+/g, '');
            return (/^[\],:{}\s]*$/).test(str);
        },

        blank: function(str) {
            return /^\s*$/.test(this);
        }
    };
});