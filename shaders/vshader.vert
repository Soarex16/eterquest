#version 120

attribute vec3 vertex;
attribute vec2 _texCoord;
varying vec2 texCoord;

uniform mat4 transformation;
uniform vec4 projection = vec4(1.0, 1.0, 1.0, 1.0);

vec3 project(vec3 v) {
    float x = v.x;
    float y = v.y;
    float z = v.z;
    if(z < 0) {
        x *= -1;
        y *= -1;
    }
    return vec3(x / (z * projection.x), y / (z * projection.y), -1.0 + (z + projection.z) / projection.w);
}

void main() {
    vec3 ortho = (transformation * vec4(vertex, 1.0)).xyz;
    gl_Position = vec4(project(ortho), 1.0);
    texCoord = _texCoord;
}