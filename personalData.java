import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.Scanner;

public class personalData {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ФИО: ");
        String fullName = scanner.nextLine().trim();

        // разбиваем ФИО на фамилию, имя и отчество
        int firstSpaceIndex = fullName.indexOf(' ');
        int lastSpaceIndex = fullName.lastIndexOf(' ');

        if (firstSpaceIndex == -1 || lastSpaceIndex == -1 || firstSpaceIndex == lastSpaceIndex) {
            System.out.println("Пожалуйста, введите ФИО полностью (фамилия, имя, отчество)");
            return;
        }

        String surname = fullName.substring(0, firstSpaceIndex).trim();
        String name = fullName.substring(firstSpaceIndex + 1, lastSpaceIndex).trim();
        String patronymic = fullName.substring(lastSpaceIndex + 1).trim();

        System.out.print("Введите дату рождения (дд.мм.гггг или дд/мм/гггг): ");
        String Date_of_birth_Inp = scanner.nextLine().trim();

        // собираем дату рождения
        LocalDate Date_of_birth = null;
        DateTimeFormatter form = DateTimeFormatter.ofPattern("[d.M.yyyy][d/M/yyyy]");
        try {
            Date_of_birth = LocalDate.parse(Date_of_birth_Inp, form);
        } catch (Exception e) {
            System.out.println("Некорректный формат ввода даты");
            return;
        }

        if (Date_of_birth.isAfter(LocalDate.now())) {
            System.out.println("Дата рождения не может быть в будущем");
            return;
        }

        // инициалы
        String initials = surname + " " + getInitials(name, patronymic);
        System.out.println("Инициалы: " + initials);

        // определение пола по отчеству
        String gender = genderIndentification(patronymic);
        System.out.println("Пол: " + gender);

        // определение возраста
        int age = ageIndentification(Date_of_birth, LocalDate.now());
        String ageString = age + " " + getAgeWord(age);
        System.out.println("Возраст: " + ageString);
    }

    // метод для получения инициалов
    private static String getInitials(String name, String patronymic) {
        StringBuilder initialsResult = new StringBuilder();

        String[] nameParts = name.trim().split("[-\\s]+");
        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initialsResult.append(Character.toUpperCase(part.charAt(0))).append(".");
            }
        }

        String[] patronymicParts = patronymic.trim().split("[-\\s]+");
        for (String part : patronymicParts) {
            if (!part.isEmpty()) {
                initialsResult.append(Character.toUpperCase(part.charAt(0))).append(".");
            }
        }

        return initialsResult.toString();
    }

    // метод для определения пола по отчеству
    private static String genderIndentification(String patronymic) {
        if (patronymic.endsWith("ич") || patronymic.endsWith("ов")) {
            return "М";
        } else if (patronymic.endsWith("на") || patronymic.endsWith("ова")) {
            return "Ж";
        } else {
            return "ОПРЕДЕЛИТЬ_НЕ_УДАЛОСЬ";
        }
    }

    // метод для определения возраста
    private static int ageIndentification(LocalDate Date_of_birth, LocalDate actualDate) {
        if (Date_of_birth != null && actualDate != null) {
            return Period.between(Date_of_birth, actualDate).getYears();
        } else {
            return 0;
        }
    }

    // метод для получения правильного окончания для введённого возраста
    private static String getAgeWord(int age) {
        int lastTwoDigits = age % 100;
        int lastDigit = age % 10;
        String ageWord;

        switch (lastTwoDigits) {
            case 11:
            case 12:
            case 13:
            case 14:
                ageWord = "лет";
                break;
            default:
                switch (lastDigit) {
                    case 1:
                        ageWord = "год";
                        break;
                    case 2:
                    case 3:
                    case 4:
                        ageWord = "года";
                        break;
                    default:
                        ageWord = "лет";
                        break;
                }
                break;
        }
        return ageWord;
    }
}
