# Simple 3D
Simple3D is a lightweight, three dimensional, graphics engine, I've developed (using only Java Standard Libraries), combined with some code I've written to procedurally generate varying scenery for it to render. The user is free to move the camera along all three (extrinsic) axis, and is permitted to alter the camera's pitch yaw and roll (relative to the extrinsic axis set of the camera itself). 
<hr/>

Below are some renders generated using this engine to show it's flexibility. This images have been cropped from this code's output (with differing presets).

![alt tag](https://raw.githubusercontent.com/rjhunjhunwala/Simple3D/master/renders.png)

While the bottom row focuses on its original primitive state, the top two rows showcase some livelier renders. Notably, the first rendering is a rendering of a computer generated landscape using layered (fractal) two-dimensional Perlin noise as a heightmap. The second image showcases the texture mapping feature (which can be used only if the polygon count of the scene is low), while the rest play with differing color schemes. The fifth image (middle image), showcases how arbitrarily large polygon counts can simulate smooth objects. 
