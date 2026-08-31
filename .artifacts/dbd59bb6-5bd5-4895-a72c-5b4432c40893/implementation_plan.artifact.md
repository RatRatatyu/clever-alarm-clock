# Fix "Dismissed Today" Badge and Logic

The "Dismissed for Today" badge does not appear because of conflicting database updates and a bug in the UI code. This plan fixes the logic in the UseCases and improves the UI to show a proper badge.

## User Review Required

> [!IMPORTANT]
> I will be modifying `ScheduleAlarmUseCase` to stop blindly resetting `lastDismissed` to `null`. Instead, the caller will be responsible for providing the alarm state they want to persist.

## Proposed Changes

### [Domain Logic Component]

#### [MODIFY] [ScheduleAlarmUseCase.kt](file:///C:/Users/Marga/Documents/Progects/MobileApp/Android/CleverAlarmClock/app/src/main/java/com/example/cleveralarmclock/core/domain/usecase/schedule/ScheduleAlarmUseCase.kt)
- Remove the line `lastDismissed = null` inside the `copy` block.
- Just update the alarm as it is passed to the use case.

#### [MODIFY] [DismissTodayUseCase.kt](file:///C:/Users/Marga/Documents/Progects/MobileApp/Android/CleverAlarmClock/app/src/main/java/com/example/cleveralarmclock/core/domain/usecase/schedule/DismissTodayUseCase.kt)
- Pass the updated alarm (with `lastDismissed` set) to `scheduleAlarmUseCase`.
- Remove the redundant `alarmRepository.updateAlarm` call if `ScheduleAlarmUseCase` already handles it.

#### [MODIFY] [ToggleAlarmUseCase.kt](file:///C:/Users/Marga/Documents/Progects/MobileApp/Android/CleverAlarmClock/app/src/main/java/com/example/cleveralarmclock/core/domain/usecase/manage/ToggleAlarmUseCase.kt)
- Explicitly set `lastDismissed = null` when toggling the alarm if we want to reset the dismissal state when the alarm is manually toggled.

### [UI Component]

#### [MODIFY] [CardScheduleAlarm.kt](file:///C:/Users/Marga/Documents/Progects/MobileApp/Android/CleverAlarmClock/app/src/main/java/com/example/cleveralarmclock/presentation/mainScreenFeature/components/CardScheduleAlarm.kt)
- Fix the `containerColor` assignment (actually use the dimmed color if `lastDismissed` is not null).
- Wrap the "Dismissed for..." text in a `Surface` or `Card` to make it look like a "plate" (badge).
- Adjust alpha/transparency of the card when dismissed to give better visual feedback.

## Verification Plan

### Automated Tests
- Run the app and trigger "Dismiss Today" for an alarm.
- Verify that the badge appears on the card.
- Verify that toggling the alarm (OFF/ON) removes the badge.

### Manual Verification
- Check the UI layout of the badge to ensure it looks like a "плашка" as requested.
