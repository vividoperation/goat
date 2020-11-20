const waitMillisecondsAsync = time => new Promise((resolve, _reject) => {
    setTimeout(_ => resolve(), time)
});

export {
    waitMillisecondsAsync
}