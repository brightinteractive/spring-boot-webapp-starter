/*
 --------------------------------------------------------------------
 Function for setting the correct path to our fixtures
 --------------------------------------------------------------------
 */

import 'jasmine-jquery';

function setFixturesPath() {
    var path = '';

    if (typeof window.__karma__ !== 'undefined') {
        path += 'base/';
    }

    jasmine.getFixtures().fixturesPath = path + 'src/test/js/fixtures';
}

export default setFixturesPath;
