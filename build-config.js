var jsSrcDir = 'src/main/js';
var staticOutDir = 'src/main/resources/static/static';

module.exports = {
    jsSrcDir: jsSrcDir,
    absJsSrcDir: __dirname + '/' + jsSrcDir,
    testJsSrcDir: 'src/test/js',
    jsOutDir: staticOutDir + '/js',
    cssOutDir: staticOutDir + '/css'
};
