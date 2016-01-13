var webpack = require('webpack');
var buildConfig = require('./build-config');

module.exports = {
    context: buildConfig.absJsSrcDir,
    entry: {
        app: './app.js'
    },
    output: {
        path: __dirname + '/' + buildConfig.jsOutDir,
        filename: '[name]-bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel'
            }
        ]
    },
    plugins: [
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.UglifyJsPlugin()
    ],
    devtool: 'source-map'
};
