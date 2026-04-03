# SoftwareQuality
Jabberpoint assignment for Software Quality class 2026
##
Ian Donker 4629981
###
Maven used for testing, github actions for CI/CD
###
Introduced a factory, observer and strategy pattern to refactor existing codebase.
Overall SOLID compliance analysis
Principle	Before Refactoring	After Refactoring
Single Responsibility	
	Presentation manages data + UI updates
	Slide contains data + rendering logic
	MenuController creates UI + handles business logic	
	Presentation manages only data
	Observers handle their own updates
	Each class has single, clear purpose
Open/Closed
	instanceof checks require modification
	Adding file formats modifies XMLAccessor
	New item types modify save logic	
	New observers added without changing Presentation
	New file formats via new Reader/Writer
	New items via factory registration
Liskov Substitution	DemoPresentation.saveFile() throws exception
	Cannot substitute all Accessor implementations	
	All PresentationReader implementations work correctly
	All implementations honor their contracts
Interface Segregation	
	Accessor forces both load + save
	Read-only sources must implement save	
	Separate Reader and Writer interfaces
	Clients depend only on what they need
Dependency Inversion	
	Presentation → SlideViewerComponent
	MenuController → XMLAccessor
	Direct instantiation everywhere		Presentation → PresentationObserver
	MenuController → Reader/Writer interfaces
	Dependencies on abstractions


[![CI](https://github.com/Akolix/SoftwareQuality/actions/workflows/ci.yml/badge.svg)](https://github.com/Akolix/SoftwareQuality/actions/workflows/ci.yml)
