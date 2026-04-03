# SoftwareQuality

Jabberpoint assignment for Software Quality class 2026

## Project Information

**Author:** Ian Donker (4629981)

**Build Tools:** Maven for dependency management and testing

**CI/CD:** GitHub Actions for continuous integration and deployment

## Project Overview

This project is a comprehensive refactoring of the JabberPoint presentation application, transforming legacy code into a maintainable, extensible system that adheres to SOLID principles through the implementation of three fundamental design patterns.

[![CI](https://github.com/Akolix/SoftwareQuality/actions/workflows/ci.yml/badge.svg)](https://github.com/Akolix/SoftwareQuality/actions/workflows/ci.yml)

## Refactoring Overview

The codebase has been refactored using three design patterns:

1. **Observer Pattern** - Decouples the domain model from UI components
2. **Strategy Pattern** - Provides extensible file I/O with segregated interfaces
3. **Factory Pattern** - Centralizes object creation and eliminates type checking

### Implementation Branches

- `observer-pattern` - Observer pattern implementation
- `strategy-pattern` - Strategy pattern implementation
- `factory-pattern` - Factory pattern implementation

## Design Patterns Implemented

### 1. Observer Pattern

**Purpose:** Decouple Presentation domain model from SlideViewerComponent UI

**Key Changes:**
- Added `PresentationObserver` interface
- Presentation manages list of observers and notifies on state changes
- SlideViewerComponent implements PresentationObserver
- Removed direct coupling between domain and UI layers
- Removed inappropriate `exit()` method from Presentation class

**Benefits:**
- Presentation can be tested without UI dependencies
- Multiple views can observe the same presentation
- Domain model is completely independent of UI framework
- Easy to add new view types (thumbnails, outline, presenter notes)

### 2. Strategy Pattern

**Purpose:** Make file I/O extensible and respect Interface Segregation Principle

**Key Changes:**
- Split `Accessor` abstract class into `PresentationReader` and `PresentationWriter` interfaces
- `XMLAccessor` became `XMLPresentationReader` and `XMLPresentationWriter`
- `DemoPresentation` became `DemoPresentationReader` (honest read-only implementation)
- MenuController and JabberPoint accept strategies via dependency injection

**Benefits:**
- Easy to add new file formats (JSON, YAML, PowerPoint)
- Read-only sources don't implement fake write methods
- Write-only destinations don't implement fake read methods
- Can mix strategies (read XML, write JSON)
- No Liskov Substitution Principle violations

### 3. Factory Pattern

**Purpose:** Eliminate instanceof checks and centralize object creation

**Key Changes:**
- Added `SlideItemFactory` for centralized item creation
- Added `ItemCreator` functional interface
- SlideItem hierarchy enhanced with `getType()` and `getContent()` methods
- XMLPresentationReader uses factory instead of direct instantiation
- XMLPresentationWriter uses polymorphism instead of instanceof checks

**Benefits:**
- Zero instanceof checks (reduced from 4)
- Zero type casts (reduced from 4)
- New item types can be added via registration without modifying existing code
- Each item encapsulates its own serialization logic
- Type-safe at compile time

## SOLID Principles Compliance

### Overall SOLID Compliance Analysis

| Principle | Before Refactoring | After Refactoring |
|-----------|-------------------|-------------------|
| **Single Responsibility** | Presentation manages data + UI updates; Slide contains data + rendering logic; MenuController creates UI + handles business logic | Presentation manages only data; Observers handle their own updates; Each class has single, clear purpose |
| **Open/Closed** | instanceof checks require modification; Adding file formats modifies XMLAccessor; New item types modify save logic | New observers added without changing Presentation; New file formats via new Reader/Writer; New items via factory registration |
| **Liskov Substitution** | DemoPresentation.saveFile() throws exception; Cannot substitute all Accessor implementations | All PresentationReader implementations work correctly; All implementations honor their contracts |
| **Interface Segregation** | Accessor forces both load + save; Read-only sources must implement save | Separate Reader and Writer interfaces; Clients depend only on what they need |
| **Dependency Inversion** | Presentation → SlideViewerComponent; MenuController → XMLAccessor; Direct instantiation everywhere | Presentation → PresentationObserver; MenuController → Reader/Writer interfaces; Dependencies on abstractions |

### Detailed SOLID Improvements

#### Single Responsibility Principle (SRP)

**Before:**
- Presentation class mixed domain logic with UI updates
- Slide class contained both data and rendering concerns
- MenuController handled both UI creation and business logic

**After:**
- Presentation focuses solely on managing slide data and state
- View updates delegated to observer implementations
- Application lifecycle logic removed from domain model
- Each class has one clearly defined responsibility

#### Open/Closed Principle (OCP)

**Before:**
- Adding new slide item types required modifying XMLAccessor with new instanceof checks
- New file formats required extending and modifying Accessor class
- Closed for extension, open for modification

**After:**
- New observers can be added by implementing PresentationObserver interface
- New file formats added by implementing Reader/Writer interfaces
- New slide items registered with factory without code changes
- Open for extension, closed for modification

#### Liskov Substitution Principle (LSP)

**Before:**
- DemoPresentation violated LSP by throwing IllegalStateException in saveFile()
- Accessor subclasses could not be reliably substituted

**After:**
- DemoPresentationReader honestly implements only PresentationReader
- All implementations properly fulfill their interface contracts
- No fake methods or unexpected exceptions

#### Interface Segregation Principle (ISP)

**Before:**
- Accessor forced all implementations to provide both load and save methods
- Read-only sources had to implement write methods they couldn't support

**After:**
- Separate PresentationReader and PresentationWriter interfaces
- Clients depend only on the interfaces they need
- No forced dependencies on unused methods

#### Dependency Inversion Principle (DIP)

**Before:**
- High-level Presentation depended on low-level SlideViewerComponent
- MenuController directly instantiated XMLAccessor
- Dependencies pointed from abstractions to concrete implementations

**After:**
- Presentation depends on PresentationObserver abstraction
- MenuController depends on Reader/Writer interfaces
- XMLPresentationReader depends on SlideItemFactory abstraction
- All major dependencies point to abstractions
