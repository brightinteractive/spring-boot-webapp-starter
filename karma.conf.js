var buildConfig = require('./build-config');

module.exports = function(config) {
    'use strict';

    var webpackConfig = require('./webpack.config.js');

    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '',

        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            buildConfig.testJsSrcDir + '/**/*.spec.js',

            // HTML fixtures
            {
                pattern: buildConfig.testJsSrcDir + '/fixtures/*.html',
                watched: true,
                included: false,
                served: true
            }
        ],

        // list of files to exclude
        exclude: [
        ],

        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
            'src/test/js/**/*.spec.js': ['webpack']
        },

        webpack: {
            resolve: {
                modulesDirectories: [buildConfig.absJsSrcDir, 'web_modules', 'node_modules']
            },
            devtool: 'inline-source-map',
            module: webpackConfig.module,
            plugins: [] //webpackConfig.plugins
        },

        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: [
            'spec',
            'junit'
        ],

        junitReporter: {
            outputDir: 'build/test-results'
        },

        // web server port
        port: 9876,

        // enable / disable colors in the output (reporters and logs)
        colors: true,

        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,

        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['PhantomJS2'],

        phantomjsLauncher: {
            // Have phantomjs exit if a ResourceError is encountered (useful if karma exits without killing phantom)
            exitOnResourceError: true
        },

        // If browser does not capture in given timeout [ms], kill it
        captureTimeout: 60000,

        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false,

        plugins: [
            // these plugins will be require()d by Karma
            'karma-chrome-launcher',
            'karma-jasmine',
            'karma-junit-reporter',
            'karma-phantomjs2-launcher',
            'karma-spec-reporter',
            'karma-webpack'
        ]
    });
};
