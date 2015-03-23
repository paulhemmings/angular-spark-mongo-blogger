describe('MainApplicationModule', function () {

    var scope,
        controller,
        mockFileUpload = {
            uploadFileToUrl : function() {}
        };

    beforeEach(function () {
        module('MainApplicationModule');
    });

    beforeEach(inject(function(_fileUpload_, $q) {
        mockFileUpload = _fileUpload_;
        spyOn(mockFileUpload, "uploadFileToUrl").andCallFake(function() {
            var deferred = $q.defer();
            deferred.resolve('Remote call result');
            return deferred.promise;
        });
    }));

    describe('BloggerController', function () {

        beforeEach(inject(function ($rootScope, $controller) {
            scope = $rootScope.$new();
            controller = $controller('BloggerController', {
                '$scope': scope
            });
        }));

        it("contains spec with an expectation", function() {
            expect(true).toBe(true);
        });

        it('should add response data to scope when handling upload response', function () {
            expect(scope.responseData.length).toBe(0);
            scope['__test__'].handleUploadResponse({
                data : {
                    'uploaded-file' : 'some-test-data'
                }
            });
            expect(scope.responseData.length).toBe(1);
        });

        it('should load blogs', function() {
            expect(scope.blogs.length).toBe(0);
            expect(scope.selectedBlog).toBe(null);
            scope['__test__'].loadBlogs({data: [{'name':'test-data'}]});
            expect(scope.blogs.length).toBe(1);
            expect(scope.selectedBlog).not.toBe(null);
        });

        it('should upload a file', function() {
            scope['__test__'].uploadFile();
            mockFileUpload.uploadFileToUrl();
        });

    });
});
