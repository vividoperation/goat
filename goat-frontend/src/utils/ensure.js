// Tests to see whether a string is null or whitespace
const isNotNullOrWhitespace = input => {
    input = isNotNull(input)

    if (typeof input != "string") {
        throw new Error("input is not a string")
    }

    if (input === "") {
        throw new Error("input is an empty string")
    }

    if (input.trim() === "") {
        throw new Error ("input is all whitespace")
    }

    return input
}

// Checks to see if something is null or undefined
const isNotNull = input => {
    if (input === null) {
        throw new Error("input is null")
    }

    if (typeof input === 'undefined') {
        throw new Error("input is undefined")
    }

    return input
}

// Checks to see if an array is null or empty
const isNotNullOrEmpty = input => {
    input = isNotNull(input);

    if (Array.isArray(input)) {
        throw new Error("input is not an array")
    }

    if (!input.length) {
        throw new Error("input is empty")
    }

    return input
}

export {
    isNotNull,
    isNotNullOrEmpty,
    isNotNullOrWhitespace
}