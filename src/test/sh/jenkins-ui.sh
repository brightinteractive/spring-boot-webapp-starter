# Front end build tools and quality checks with grunt

if [ package.json -nt node_modules ]; then
   rm -fr node_modules
fi
npm install --no-color
grunt test --no-color --verbose
