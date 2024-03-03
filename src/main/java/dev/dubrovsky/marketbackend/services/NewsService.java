package dev.dubrovsky.marketbackend.services;

import dev.dubrovsky.marketbackend.models.News;
import dev.dubrovsky.marketbackend.repositories.NewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News getById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Transactional
    public News add(News news) {
        news.setDateOfPublication(LocalDate.now());
        return newsRepository.save(news);
    }

    @Transactional
    public News update(Long id, News newsNew) {
        var newsOld = newsRepository.findById(id).orElse(null);
        if (newsOld != null) {
            BeanUtils.copyProperties(newsNew, newsOld, "newsId", "dateOfPublication");
            return newsRepository.save(newsOld);
        }
        return null;
    }

    @Transactional
    public News delete(Long id) {
        var news = newsRepository.findById(id).orElse(null);
        if (news == null) return null;
        newsRepository.delete(news);
        return news;
    }

}
