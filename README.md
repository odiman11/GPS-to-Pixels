# ConverGpsToPixels

- [Introduction](#introduction)
- [Installation](#installation)
- [Usage](#usage)
- [Parameters](#parameters)
- [Optional Constants](#optional-constants)
- [Distance Calculation](#distance-calculation)
- [References](#references)
- [Limitation](#limitations)
- [License](#license)

## Introduction

The `ConverGpsToPixels` class is a Java class that converts GPS coordinates into corresponding pixel coordinates on a map image using the Mercator projection formula.
This can be useful for displaying the location of GPS coordinates on a graphical user interface, such as a map application. 
It also calculates the distance between two GPS coordinates using the Haversine formula.

## Installation

To use the `ConverGpsToPixels` class, simply download the `ConverGpsToPixels.java` file and add it to your Java project. 
Alternatively, you can clone this repository using the following command:

`git clone https://github.com/odiman11/GPS-to-Pixels.git`

## Usage

To use the `ConverGpsToPixels` class, simply instantiate an object of the class and call the `convert` method, passing in the necessary parameters

```java
ConverGpsToPixels converter = new ConverGpsToPixels();
Point2D.Double targetPixel = converter.convert(targetLat, targetLong, hostLat, hostLong, screenWidth, screenHeight);
```

The convert method returns a Point2D.Double object that contains the x and y pixel coordinates of the target location on the screen.

## Parameters
The convert method requires the following parameters:

* targetLat	double	- The latitude of the target GPS coordinate.
* targetLong	double	- The longitude of the target GPS coordinate.
* hostLat	double	- The latitude of the host GPS coordinate (i.e., the center of the map).
* hostLong	double -	The longitude of the host GPS coordinate.
* screenWidth	int -	The width of the screen in pixels.
* screenHeight -	int	The height of the screen in pixels.


## Optional Constants
The ConverGpsToPixels class includes two optional constants that can be used to modify the behavior of the conversion process:

* EARTH_RADIUS	double	- The radius of the earth.
* RANGE_NAUTICAL_MILE	int	The zoom level of the map.


## Distance Calculation
The convert method also calculates the distance between the target and host GPS coordinates using the Haversine formula.
This distance is returned in kilometers or miles, depending the 'EARTH_RADIUS' constant.

## References
The ConverGpsToPixels class was based on the Mercator projection formula and the Haversine formula. For more information, see the following resources:

* [Mercator projection](https://en.wikipedia.org/wiki/Mercator_projection)
* [Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula)

## Limitations

It is important to note that the `ConverGpsToPixels` class is designed for use with maps that use the Mercator projection.
It may not be suitable for use with other types of maps or projections.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/odiman11/GPS-to-Pixels/blob/master/LICENSE) file for details.
