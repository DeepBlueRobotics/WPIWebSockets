function contains(props, propname) {
    for(const [pname, prop] of Object.entries(props)) {
        if(pname === propname) {
            return true;
        }
    }
    return false;
}

function filterProps(props) {
    if(contains(props, "<init")) {
        delete props["<init"];
    }
    return props;
}

function aAn(str) {
    //Credit: https://stackoverflow.com/questions/37896484/multiple-conditions-for-javascript-includes-method
    return ['a', 'e', 'i', 'o', 'u'].some(el => str.includes(el));
}

module.exports = { contains, filterProps, aAn }
