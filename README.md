![Maven Central: Photo Calendar](https://img.shields.io/maven-central/v/io.github.dongchyeon/photo-calendar?color=orange&label=Maven%20Central%20%3A%20Photo%20Calendar)
![Minimum SDK: 24](https://img.shields.io/badge/Minimum%20SDK-24-yellowgreen)
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>

## Photo Calendar - A customizable Jetpack Compose calendar that allows you to add background image to specific days.

![photo_calendar](https://github.com/user-attachments/assets/f46a7b48-367a-4e3f-b4ee-f9e1fe053565)

A fully customizable calendar component for Jetpack Compose.<br>
It supports both horizontal scrolling calendars and grid-based calendars.<br>
This library lets you add rich visual elements, like background images for specific days.<br>
It also provides extensive configuration options for styling and localization.

특정 날짜에 배경 이미지를 추가, Styling과 Localization 등 다양한 설정 옵션을 제공하는 Jetpack Compose를 이용한 캘린더 컴포넌트입니다.

Grid-based Calendar              |  Horizontal Calendar
:-------------------------:|:-------------------------:
![](https://github.com/user-attachments/assets/13eed3e4-0de4-45db-b916-ce66602d9402)  |  ![](https://github.com/user-attachments/assets/853cbff1-b381-4f7e-8918-9f5748f30a76)

## Features

#### 1. Customizable Styling
- Header Configuration: Customize the text style, color, and layout of the month and year display.
- Week Header Configuration: Adjust weekday labels (e.g., Sun, Mon, etc.) with different styles and colors.
- Day Configuration: Set the text style, color, and background for both regular and selected days.
- Day Indicator: Add customizable shapes (e.g., CircleShape, RoundedCornerShape) and colors to selected day.

#### 2. Localization Support
- Supports both English (EN) and Korean (KO) locales.
- Dynamically formats headers and weekday labels:
- EN: “February 2024”
  KO: “2024년 2월”

#### 3. Day Background Images
- Customize the background of specific days using events.

#### 4. Layouts
- Horizontal Scrolling Calendar: Week-based, scrollable layout.
- Grid-based Calendar: Month view with weeks in rows.

## Getting Started

Add the following to your build.gradle file:

```gradle
// Project level build.gradle
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// App level build.gradle
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
    implementation("io.github.dongchyeon:photo-calendar:<latest_version>")
}
```

## Usage

### 🚀 **Migration Update**  
Replace usages of `java.time.LocalDate` with `kotlinx.datetime.LocalDate`:
```kotlin
// Before
import java.time.LocalDate

// After
import kotlinx.datetime.LocalDate
```

#### Grid-based Calendar Example

```kotlin
var selectedDate by remember { mutableStateOf(LocalDate.now()) }

val events = listOf(
    CalendarEvent(
        date = LocalDate.now().plus(DatePeriod(days = 1)),
        imgUrl = "https://picsum.photos/200/300",
        imgShape = CircleShape
    ),
    CalendarEvent(
        date = LocalDate.now().plus(DatePeriod(days = 2)),
        imgUrl = "https://picsum.photos/200/300",
        imgShape = RoundedCornerShape(8.dp)
    ),
)

Calendar(
    selectedDate = selectedDate,
    events = events,
) {
    selectedDate = it
}
```

#### Horizontal Calendar Example

```kotlin
var selectedDate by remember { mutableStateOf(LocalDate.now()) }

val events = listOf(
    CalendarEvent(
        date = LocalDate.now().plus(DatePeriod(days = 1)),
        imgUrl = "https://picsum.photos/200/300",
        imgShape = CircleShape
    ),
    CalendarEvent(
        date = LocalDate.now().plus(DatePeriod(days = 2)),
        imgUrl = "https://picsum.photos/200/300",
        imgShape = RoundedCornerShape(8.dp)
    ),
)

HorizontalCalendar(
    selectedDate = selectedDate,
    events = events,
) {
    selectedDate = it
}
```

### Customized Options

#### 1. Adding Events (Add background image to specific days)

You can add events to specific dates using the CalendarEvent class. Each event can include a background image and a shape.

```kotlin
val events = listOf(
    CalendarEvent(
        date = LocalDate.now().plus(DatePeriod(days = 1)),
        imgUrl = "https://picsum.photos/200/300",
        imgShape = CircleShape
    ),
    CalendarEvent(
        date = LocalDate.now().plus(DatePeriod(days = 3)),
        imgUrl = "https://picsum.photos/200/300",
        imgShape = RoundedCornerShape(8.dp)
    ),
)

HorizontalCalendar(
    selectedDate = LocalDate.now(),
    events = events,
    onDayClicked = { selectedDate ->
        println("Selected Date: $selectedDate")
    }
)
```

#### 2. Header Configuration

```kotlin
Calendar(
    // ...
    headerConfig = CalendarHeaderConfig.default().copy(
        textStyle = Typography.titleLarge,
        textColor = Color.Red
    )
    // ...
)
```

#### 3. Week Header Configuration

```kotlin
Calendar(
    // ...
    weekHeaderConfig = CalendarWeekHeaderConfig.default().copy(
        textStyle = Typography.bodyLarge,
        textColor = Color.Gray
    )
    // ...
)
```

#### 4. Day Configuration

```kotlin
Calendar(
    // ...
    dayConfig = CalendarDayConfig.default().copy(
        dayTextColor = Color.Blue,
        selectedDayTextColor = Color.White
    )
    // ...
)
```

#### 5. Indicator Configuration

```kotlin
Calendar(
    // ...
    indicatorConfig = CalendarIndicatorConfig(
        indicatorColor = Color.Yellow.copy(alpha = 0.4f),
        shape = RoundedCornerShape(12.dp)
    )
    // ...
)
```

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

### Contributions, bug reports, and feature requests are welcome!
