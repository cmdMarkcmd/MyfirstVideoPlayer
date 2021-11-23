
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("uv2")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.


add_library( # Sets the name of the library.
        uv2

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        uv2.cpp)
add_library(
        avcodec
        SHARED
        IMPORTED
)
set_target_properties(
        avcodec
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavcodec.so
)
add_library(
        x264
        SHARED
        IMPORTED
)
set_target_properties(
        x264
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libx264.so
)

add_library(
        postproc
        SHARED
        IMPORTED
)
set_target_properties(
        postproc
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libpostproc.so
)

add_library(
        avdevice
        SHARED
        IMPORTED
)
set_target_properties(
        avdevice
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavdevice.so
)

add_library(
        avfilter
        SHARED
        IMPORTED
)
set_target_properties(
        avfilter
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavfilter.so
)

add_library(
        avformat
        SHARED
        IMPORTED
)
set_target_properties(
        avformat
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavformat.so
)

add_library(
        avutil
        SHARED
        IMPORTED
)
set_target_properties(
        avutil
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavutil.so
)

add_library(
        swresample
        SHARED
        IMPORTED
)
set_target_properties(
        swresample
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libswresample.so
)

add_library(
        swscale
        SHARED
        IMPORTED
)
set_target_properties(
        swscale
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libswscale.so
)

include_directories(${CMAKE_SOURCE_DIR}/include)
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        uv2

        x264
        swscale
        avcodec
        avdevice
        postproc
        avfilter
        avformat
        avutil
        swresample
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})


include_directories(${CMAKE_SOURCE_DIR}/fftools)

#引入ffmpeg.h与cmdutils.h
add_library(
        ffmpeg
        SHARED
        fftools/cmdutils.c
        fftools/ffmpeg.c
        fftools/ffmpeg_filter.c
        fftools/ffmpeg_hw.c
        fftools/ffmpeg_opt.c
)

target_link_libraries(
        ffmpeg
        avfilter
        avformat
        avdevice
        avcodec
        avutil
        x264
        swresample
        swscale
        postproc
        ${log-lib})

target_link_libraries(
        uv2
        ffmpeg
        ${log-lib})
