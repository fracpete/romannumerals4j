# romannumerals4j

Tiny Java library for formatting and parsing Roman numerals, sourced from 
various StackOverflow posts.

Released under [CC-BY-SA 3.0](https://creativecommons.org/licenses/by-sa/3.0/legalcode.txt).

## Usage

### Formatting
Formatting numbers (1-3999 only):

```java
import com.github.fracpete.romannumerals4j.RomanNumeralFormat;
...
RomanNumeralFormat f = new RomanNumeralFormat();
System.out.println(f.format(57));
```

Outputs:
```
LVII
```

### Parsing
Parsing numbers:

```java
import com.github.fracpete.romannumerals4j.RomanNumeralFormat;
...
RomanNumeralFormat f = new RomanNumeralFormat();
System.out.println(f.parse("LVII"));
```

Outputs:
```
57
```

## Maven
Use the following dependency in your Maven project:

```xml
<dependency>
    <groupId>com.github.fracpete</groupId>
    <artifactId>romannumerals4j</artifactId>
    <version>0.0.1</version>
</dependency>
```
