module.exports = function(grunt) {

    // 1. All configuration goes here

    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),

        project: {
            root: './src/main/webapp',
            public: '<%= project.root %>/public',
            app: '<%= project.root %>/app',
            scss: [
                '<%= project.public %>/scss/*.scss'
            ],
            js: [
                '<%= project.public %>/scripts/*.js',
                '<%= project.app %>/**/*.js'
            ]
        },

        karma: {
            unit: {
                configFile: './src/test/webapp/karma.client.config.js',
                singleRun: true,
                plugins:[
                    'karma-jasmine',
                    'karma-coverage',
                    'karma-chrome-launcher',
                    'karma-phantomjs-launcher'
                ]
            }
        },

        sass : {
            dev: {
                options: {
                    style: 'expanded',
                    compass: false
                },
                files: {
                    '<%= project.public %>/styles/blog.css': '<%= project.scss %>'
                }
            },
            dist: {
                options: {
                    style: 'compressed',
                    compass: true
                },
                files: {
                    '<%= project.public %>/styles/global.css': '<%= project.scss %>'
                }
            }
        },

        watch: {
            sass: {
                files: '<%= project.public %>/scss/{,*/}*.{scss,sass}',
                tasks: ['sass:dev']
            }
        },

        concat: {
            dist: {
                src: [
                    './src/main/resources/public/scripts/*.js', // All public scripts
                    './src/main/resources/app/**/*.js'   // All the app scripts
                ],
                dest: './src/main/resources/build/production.js'
            }
        },

        uglify: {
            build: {
                src: './src/main/resources/build/production.js',
                dest: './src/main/resources/build/production.min.js'
            }
        }

    });

    // 3. Where we tell Grunt which plugins we intend to use

    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-watch');

    // 4. Where we tell Grunt what to do when we type "grunt" into the terminal.

    grunt.registerTask('default', ['karma', 'watch:sass' ]);

    // 5. Where we tell Grunt what other tasks to run

    grunt.registerTask('test', ['karma']);
    grunt.registerTask('build', ['sass:dev', 'karma']);
    grunt.registerTask('pre-deploy', ['sass:dist', 'concat', 'uglify']);

};
