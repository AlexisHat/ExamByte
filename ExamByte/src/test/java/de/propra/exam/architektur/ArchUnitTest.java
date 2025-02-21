package de.propra.exam.architektur;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.propra.exam.ExamByteApplication;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;


@AnalyzeClasses(packagesOf = ExamByteApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {
    @ArchTest
    ArchRule onionTest = onionArchitecture()
                    .domainModels("..domain..")
                    .domainServices("..domain..")
                    .applicationServices("..application.service..")
                    .adapter("web", "..controllers..")
                    .adapter("persistence", "..persistence..")
            .ignoreDependency("..","..DTO..")
            .ignoreDependency("..DTO..","..");


}
