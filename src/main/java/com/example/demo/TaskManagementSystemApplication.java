package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementSystemApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}
    
    //  @Bean
    // CommandLineRunner run(UserRepository userRepository,ProjectRepository projectRepo, TaskRepository taskRepo) {
    //     return args -> {
	// 		User user = new User("samruddhi@example.com", null, "Samruddhi", "password123");
    //         userRepository.save(user);
 
    //         // Create sample project
    //         // Assuming Project constructor: Project(String name, String description, LocalDate startDate, LocalDate endDate, User owner)
    //         Project project = new Project(
    //             "My First Project",
    //             "Sample project description",
    //             java.time.LocalDate.now(),
    //             java.time.LocalDate.now().plusDays(30),
    //             user  // Replace 'null' with a valid User object if needed
    //         );
    //         projectRepo.save(project);

    //         // Create sample task
    //         // Assuming Status is an enum and you have a default value, e.g., Status.TODO
    //         Task task = new Task(
    //             "Task 1",
    //             "Sample task description",
    //             java.time.LocalDate.now().plusDays(7),
    //             Status.TO_DO, // Replace with your actual Status enum value
    //             project
    //         );
    //         taskRepo.save(task);

    //         System.out.println("Project and Task saved successfully!");
    //     };
//}
 
}