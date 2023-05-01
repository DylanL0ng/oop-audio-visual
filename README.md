# Audio-Visualiser Project using Processing in Java

Name: Callum O'Brien

Student Number: C21306503

Class Group: TU856

Name: Rokas Galinauskas

Student Number: C21351826

Class Group: TU856

Name: Martin Januska

Student Number: 

Class Group: TU856

Name: Dylan Hussain

Student Number: 

Class Group: TU856

# Description of the Assignment
An assignment using the processing library in Java to create a program which reacts to the sounds of 'Aria Math' by C418, a song from Minecraft.
The program is interactive and allows the user to switch between our scenes.

# Video

# Instructions
Run the program, and it will begin playing automatically. You can pause and play the program using the space bar, and can switch between the scenes using the numbers 1-4.
- Scene 1: Callum's Scene
- Scene 2: Rokas' Scene
- Scene 3: Martin's Scene
- Scene 4: Dylan's Scene

# How it works

Callum: My scene is a modified version of Conway's Game of Life, which dynamically creates new shapes on the board based on the frequencies and volumes during the song.
When an increase in volume is detected, the frequencies at that moment are analysed, and the frequency determines the position in which the new shape will be generated. 
A shape is then chosen at random and drawn on the canvas at the predetermined position. As long as the song is playing, this Game of Life will continue indefinitely.

Rokas: My scene is an homage to the oldschool audio visualisers. Initially, a range of frequencies is generated based on the song. Each frequency band is then analysed and used to create a 'tower of squares' (pixels). The size of the tower is determined by the frequency (higher frequency means more squares and vice versa). This starts at the left side of the screen and finishes on the right side. The program continues to repeat this process until the song is over

Martin:

Dylan:

# List of classes/assets 
| Class/asset | Source |
|-----------|-----------|
| LifeBoard.java | Heavily Modified from [here](https://github.com/skooter500/OOP-2023/tree/game_of_life_fixed) |
| rokas.java | etc |
| martin.java | etc |
| dylan.java | etc |

# What we are most proud of in the assignment

Callum: I find it fascinating how such a simple algorithm can create such stunning and unique visuals, and I thought that paired perfectly with our choice of song. Aria Math is simple, yet evokes such emotion, particularly due to the sheer sense of nostalgia which arises when we listen to songs from Minecraft. In a way, these songs are the soundtrack of many of our childhoods, and I feel like these feelings which arise from this song are matched so perfectly by Conway’s game of life.

Rokas: The song selection brought about a wave of nostalgia for me, as it was from a childhood game. While I've worked on numerous coding projects in the past, none have evoked any sort of emotional response. However, this time around, we aimed to create a different experience by using visualizers to try and stir emotions in the user. In my attempt, I sensed a feeling of nostalgia, much like the one I felt when choosing the song. Although the user may not recognize its origin, the design may be reminiscent of something they have seen before.

Martin: 

Dylan: 

# References
[Processing Documentation](https://processing.org/reference/)