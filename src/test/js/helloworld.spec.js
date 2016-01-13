import '../../main/js/polyfills';
import helloworld from '../../main/js/helloworld';

// hello world test
describe('hello world', function(){
    'use strict';

    it('should return hello world', function(){
        expect(helloworld()).toEqual('hello world');
    });
});
