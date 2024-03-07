import numpy as np
import plotly
import plotly.graph_objects as go
from plotly.colors import label_rgb

def get_colors(img, ver):
    h, w, _ = img.shape
    ver[0, :] = np.minimum(np.maximum(ver[0, :], 0), w - 1)  # x
    ver[1, :] = np.minimum(np.maximum(ver[1, :], 0), h - 1)  # y
    ind = np.round(ver).astype(np.int32)
    colors = img[ind[1, :], ind[0, :], :] / 255.  # n x 3

    return colors.copy()

def render_3D(img, ver_lst, tri, height, result_path="result.html"):

    n_face = tri.shape[0]
    for i, ver in enumerate(ver_lst):
        colors = get_colors(img, ver)

        n_vertex = ver.shape[1]

        x_axis = []
        y_axis = []
        z_axis = []
        i_axis = []
        j_axis = []
        k_axis = []
        vertexcolors = []

        for i in range(n_vertex):
            x, y, z = ver[:, i]
            x_axis.append(x)
            y_axis.append(z)
            z_axis.append(height - y)
            b, g, r = colors[i, 2], colors[i, 1], colors[i, 0]
            b, g, r = float(b) * 255.0, float(g) * 255.0, float(r) * 255.0
            vertexcolors.append(label_rgb((b,g,r)))
        for i in range(n_face):
            idx1, idx2, idx3 = tri[i]  # m x 3
            i_axis.append(int(idx1))
            j_axis.append(int(idx2))
            k_axis.append(int(idx3))
            
        # plot the trimesh and vertices of the mesh in 3D
        fig = go.Figure(data=[
                            go.Mesh3d(
                                # x, y and z give the vertices of the triangles
                                x=x_axis,
                                y=y_axis,
                                z=z_axis,
                                # the color of each vertex is given by its index in the list of vertices
                                vertexcolor=vertexcolors,
                                # i, j and k give the vertices of triangles. 3 points that connect to make a triangle
                                i=i_axis,
                                j=j_axis,
                                k=k_axis,
                                showscale=False,)
                            ])
        
        # Remove the axis information
        fig.update_layout(
            scene=dict(
                xaxis=dict(showticklabels=False),
                yaxis=dict(showticklabels=False),
                zaxis=dict(showticklabels=False),
            )
        )
        
        plotly.offline.plot(fig, filename=result_path, auto_open=False)