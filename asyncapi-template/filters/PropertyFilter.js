function formatPropName(name) {
    var sname = name.substring(name.startsWith("<>") ? 2 : 1);
    var parts = sname.split("_");
    //Credit for condtion: https://stackoverflow.com/questions/37896484/multiple-conditions-for-javascript-includes-method
    var mv1toend = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'].some(el => parts[0].includes(el));
    var out = "";
    for(var i = mv1toend ? 1 : 0; i < parts.length; i++) {
        out += cap1(parts[i]);
    }
    if(mv1toend) {
        out += cap1(parts[0]);
    }
    return out;
}

function cap1(str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
}

function formatPropType(prop) {
    if(prop.type() === "array") {
        return formatPropType(prop.items()) + "Array";
    }
    switch(prop.type()) {
        case "boolean":
            return "Boolean";
        case "integer":
            return "Integer";
        case "number":
            return "Double";
        case "string":
            return "String";
        default:
            return undefined;
    }
}

function formatPropTypeArr(prop) {
    return prop.type() === "array" ? formatPropPrimType(prop) : formatPropType(prop);
}

function formatPropPrimType(prop) {
    if(prop.type() === "array") {
        return formatPropPrimType(prop.items()) + "[]";
    }
    switch(prop.type()) {
        case "boolean":
            return "boolean";
        case "integer":
            return "int";
        case "number":
            return "double";
        case "string":
            return "String";
        default:
            return undefined;
    }
}

function formatPropInitialValue(prop) {
    if(prop.type() === "array") {
        var str = formatPropPrimType(prop);
        return "new " + str.substring(0, str.length-1) + "0]";
    }
    switch(prop.type()) {
        case "boolean":
            return "false";
        case "integer":
        case "number":
            return "0";
        case "string":
            return '""';
        default:
            return undefined;
    }
}

module.exports = { formatPropName, cap1, formatPropType, formatPropTypeArr, formatPropPrimType, formatPropInitialValue }
