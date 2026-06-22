package com.example.simplearticle.config;

import com.example.simplearticle.batch.ArticleImportRow;
import com.example.simplearticle.models.Article;
import com.example.simplearticle.repositories.ArticleRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ArticleImportBatchConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<ArticleImportRow> articleCsvReader(
            @Value("#{jobParameters['filePath']}") String filePath
    ) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Missing job parameter: filePath");
        }

        return new FlatFileItemReaderBuilder<ArticleImportRow>()
                .name("articleCsvReader")
                .resource(new FileSystemResource(filePath))
                .linesToSkip(1)
                .delimited()
                .names("title", "content")
                .targetType(ArticleImportRow.class)
                .build();
    }

    @Bean
    public ItemProcessor<ArticleImportRow, Article> articleProcessor() {
        return row -> {
            if (row.title() == null || row.title().isBlank()) {
                return null;
            }

            if (row.content() == null || row.content().isBlank()) {
                return null;
            }

            Article article = new Article();
            article.setTitle(row.title());
            article.setContent(row.content());

            return article;
        };
    }

    @Bean
    public RepositoryItemWriter<Article> articleWriter(
            ArticleRepository articleRepository
    ) {
        RepositoryItemWriter<Article> writer = new RepositoryItemWriter<>(articleRepository);
        // writer.setRepository(articleRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step importArticleStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<ArticleImportRow> articleCsvReader,
            ItemProcessor<ArticleImportRow, Article> articleProcessor,
            RepositoryItemWriter<Article> articleWriter
    ) {
        return new StepBuilder("importArticleStep", jobRepository)
                .<ArticleImportRow, Article>chunk(10)
                .reader(articleCsvReader)
                .processor(articleProcessor)
                .writer(articleWriter)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job importArticleJob(
            JobRepository jobRepository,
            Step importArticleStep
    ) {
        return new JobBuilder("importArticleJob", jobRepository)
                .start(importArticleStep)
                .build();
    }
}
