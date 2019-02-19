
<<<<<<< HEAD
# PointRegionQuadTree-Java
=======
# PointRegionQuadTree-Java  
>>>>>>> refs/remotes/origin/master

[![Build Status](https://travis-ci.org/brkyzdmr/QuadTree-Java.svg?branch=master)](https://travis-ci.org/brkyzdmr/QuadTree-Java)![GitHub commit activity](https://img.shields.io/github/commit-activity/m/brkyzdmr/QuadTree-Java.svg)[![](https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555)](https://www.linkedin.com/in/brkyzdmr/)

<br />
<p align="center">
<img src="/media/PRexamp.png" alt="Logo" width="800">
<br/>  
  <h3 align="center">Point Region Quad Tree</h3>  
  <p align="center">  
    In the Point Region Quad Tree each node either has exactly four children or is a leaf. That is, the PR Quad Tree is a full four-way branching (4-ary) tree in shape. The PR Quad Treerepresents a collection of data points in two dimensions by decomposing the region containing the data points into four equal quadrants, subquadrants, and so on, until no leaf node contains more than a single point. In other words, if a region contains zero or one data points, then it is represented by a PR Quad Tree consisting of a single leaf node. If the region contains more than a single data point, then the region is split into four equal quadrants. The corresponding PR Quad Tree then contains an internal node and four subtrees, each subtree representing a single quadrant of the region, which might in turn be split into subquadrants. Each internal node of a PR Quad Tree represents a single split of the two-dimensional region. The four quadrants of the region (or equivalently, the corresponding subtrees) are designated (in order) NW, NE, SW, and SE. Each quadrant containing more than a single point would in turn be recursively divided into subquadrants until each leaf of the corresponding PR Quad Tree contains at most one point.  
    <br/>  
    <a href="https://en.wikipedia.org/wiki/Quadtree"><strong>For more information »</strong></a>  
    <br/>  
  </p>  
</p>  
  
<!-- TABLE OF CONTENTS -->  
 ## Table of Contents  

* [Getting Started](#getting-started)  
  * [Prerequisites](#prerequisites)  
  * [Installation](#installation)  
* [Usage](#usage)  
* [License](#license)  

 ## Getting Started  
 ### Prerequisites  
You need to install these packages before run the project.
 * java-8
```bash
sudo apt install openjdk-8-jdk
```
* maven 
```bash
sudo apt install maven
```  
  
 ### Installation  
  1. Install Maven
  ```bash
sudo apt install maven
  ```
  2. Clone the repo
```bash  
git clone https://github.com/brkyzdmr/QuadTree-Java.git
```  
3. Build and run  
```bash  
mvn clean install exec:java
```  
<img src="/media/resources/cmd.gif" width="400px"</img><br>

 ## Usage  
<img src="/media/resources/app.gif" width="400px"</img><br>


* **Buttons**
  * **Ekle:** Add new node to the tree
  * **Ara:** Search in the nodes
  * **Sil:** Delete all the nodes
  * **Rastgele:** Create a random tree
  * **MouseMiddleButton**
    * **Scroll Up:**   Increase search circle size
    * **Scroll Down:**  Decrease search circle size

 ## License  
Distributed under the MIT License. See `LICENSE` for more information.  
