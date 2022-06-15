# Next Full Working Day

This repository shows you how to perform complex date manipulations with Java Date/Time API. The example, determine the next full working day with TemporalAdjuster implementation, taking into account the Turkish public holidays that include religious holidays.

## Example Usage
```java
    String nextWorkingDay = TimeHelper.findNextFullWorkingDate(LocalDate.now()); //2022-06-15
    System.out.println(nextWorkingDay);

    nextWorkingDay = TimeHelper.findNextFullWorkingDate("2022-08-29");
    System.out.println(nextWorkingDay);
```

## Output
```shell
  16-06-2022
  31-08-2022
```