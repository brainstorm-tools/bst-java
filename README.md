# bst-java
Java components of Brainstorm

* brainstorm: All the Java classes used by the Brainstorm software
   * The file brainstorm-tools/bst-java/dist/brainstorm.jar must be copied into brainstorm-tools/brainstorm3/java/brainstorm.jar
   * For the Brainstorm package downloaded from the website: this is done by a cron job on the neuroimage server
   * For a git clone: brainstorm.m downloads brainstorm.jar at startup if it is missing
* brainstorm_jogl1: [DEPRECATED] JOGL 1.0 support, build as brainstorm3/java/brainstorm_jogl1.jar (for the connectivity graphs)
* brainstorm_jogl2: [DEPRECATED] JOGL 2.0 support, build as brainstorm3/java/brainstorm_jogl2.jar (for the connectivity graphs)
* brainstorm_jogl2.3: [DEPRECATED] JOGL 2.3 support, build as brainstorm3/java/brainstorm_jogl2.3.jar (for the connectivity graphs)
* brainstorm_run_*: Runners for the compiled distribution of Brainstorm (packaged in brainstorm3/bin/brainstorm3.jar), for different Matlab versions

## License
* Some of the icons in this package have been provided courtesy of Denis Brunet (Cartool software), 
Functional BrainMapping Lab, Geneva, Switzerland
http://brainmapping.unige.ch/
