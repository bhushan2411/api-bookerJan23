package utilities;

import net.datafaker.Faker;

public class DataGenerator {

	static Faker faker = new Faker();

	public static String getEmailId() {

		String tempEmail = faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com";
		return tempEmail;

	}

	public static String getfirstName() {

		return faker.name().firstName();

	}
	
	public static String getphoneNumber() {

		return faker.number().digits(10);

	}

}
