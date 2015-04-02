angular
    .module('MainApplicationModule')
    .controller('BloggerController', ['$scope', 'fileUpload', 'bloggerService',
        function($scope, fileUpload, bloggerService) {

            $scope.uploadFile = uploadFile;
            $scope.responseData = [];

            $scope.blogs = [];
            $scope.selectedBlog = null;


            $scope.updateBlog = updateBlog;

            function updateBlog(blog) {
                //update blog
                bloggerService.updateBlog(blog);
            }

            function handleUploadResponse(response) {
                // show response
                $scope.responseData.push(response.data);
                // re-load selected blog.
                bloggerService.listBlogs().then(loadBlogs);
            }

            function uploadFile() {
                fileUpload.uploadFileToUrl($scope.selectedFile, "/upload", {
                    'blog': $scope.selectedBlog
                }).then(handleUploadResponse);
            }

            function loadBlogs(response) {
                $scope.blogs = response.data;
                $scope.selectedBlog = $scope.blogs[0];
            }

            function initialize() {
                bloggerService.listBlogs().then(loadBlogs);
            }

            initialize();

            /*
             * How do you unit test private methods?
             * Expose them via a "test" property
             */

            $scope['__test__'] = {
                handleUploadResponse: handleUploadResponse,
                loadBlogs: loadBlogs,
                uploadFile: uploadFile
            }

        }]);
