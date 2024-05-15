package dev.dubrovsky.marketbackend.service;

import dev.dubrovsky.marketbackend.model.News;
import dev.dubrovsky.marketbackend.payload.news.NewNewsPayload;
import dev.dubrovsky.marketbackend.payload.news.UpdateNewsPayload;
import dev.dubrovsky.marketbackend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final MessageSource messageSource;

    public News getById(Long id, Locale locale) {
        return findNews(id, locale);
    }

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Transactional
    public News add(NewNewsPayload newsPayload) {
        var news = new News();

        news.setTitle(newsPayload.title());
        news.setText(newsPayload.text());
        news.setDescription(newsPayload.description());
        news.setDateOfPublication(LocalDate.now());
        return newsRepository.save(news);
    }

    @Transactional
    public News update(Long id, UpdateNewsPayload newsPayload, Locale locale) {
        var newsOld = findNews(id, locale);
        if (newsOld != null) {
            BeanUtils.copyProperties(newsPayload, newsOld, "newsId", "dateOfPublication");
            return newsRepository.save(newsOld);
        }
        return null;
    }

    @Transactional
    public void delete(Long id, Locale locale) {
        var news = findNews(id, locale);
        newsRepository.delete(news);
    }

    public Integer getCount() {
        return newsRepository.findAll().size();
    }

    private News findNews(Long id, Locale locale) {
        return newsRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(messageSource.getMessage(
                        "news.errors.news_not_found",
                        new Object[]{id},
                        "News with id=" + id + " not found.",
                        locale
                ))
        );
    }

}
