/* jshint node: true */

module.exports = function(grunt) {
    "use strict";

    // Project configuration.
    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),
        clean: {
            dist: ['public/css']
        },

        recess: {
            options: {
                compile: true
            },
            bootstrap: {
                src: ['less/bootstrap.less'],
                dest: 'public/css/<%= pkg.name %>.css'
            },
            min: {
                options: {
                    compress: true
                },
                src: ['less/bootstrap.less'],
                dest: 'public/css/<%= pkg.name %>.min.css'
            }
        },

        watch: {
            recess: {
                files: 'less/*.less',
                tasks: ['recess']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-recess');
};
