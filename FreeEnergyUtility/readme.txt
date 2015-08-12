/*****************************************************************************/
/*************************Free Energy Surface Utility*************************/
/*****************************************************************************/

Author: Scott Gigante, scottgigante@gmail.com
Since: Nov 2014

This is a tool which generates a free energy surface, associated error and 
most likely folding path from a table of transition counts. Example input 
files stored in dat/out.

How to install:
copy pdf, dat, r, minimumFreeEnergy.jar
cd r
module load R
R
source("setup.r")

How to run:
cd r
module load R
module load java/1.8.0
R
source("runFreeEnergyUtility.r")

Input arguments are stored in dat/in/readfile.dat

Maintenance:
If any modifications to the Java project MinimumFreeEnergyPath, export Java 
project to MinimumFreeEnergyPath.jar as Runnable JAR file. This project 
publicly available at 
https://github.com/scottgigante/SWEN20030/tree/master/FreeEnergyUtility

Acknowledgements:
This program makes use of JGraphT.
JGraphT is a free Java class library that provides mathematical graph-theory 
objects and algorithms. It runs on Java 2 Platform (requires JDK 1.6 or later).
JGraphT may be used under the terms of either the GNU Lesser General Public 
License (LGPL) 2.1 
http://www.gnu.org/licenses/lgpl-2.1.html
or the Eclipse Public License (EPL) 
http://www.eclipse.org/org/documents/epl-v10.php
As a recipient of JGraphT, you may choose which license to receive the code 
under. For a detailed information on the dual license approach, see
https://github.com/jgrapht/jgrapht/wiki/Relicensing
Please note that JGraphT is distributed WITHOUT ANY WARRANTY; without even the 
implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. Please
refer to the license for details.