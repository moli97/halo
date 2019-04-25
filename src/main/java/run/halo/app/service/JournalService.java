package run.halo.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.halo.app.model.dto.JournalDTO;
import run.halo.app.model.dto.JournalWithCmtCountDTO;
import run.halo.app.model.entity.Journal;
import run.halo.app.model.params.JournalParam;
import run.halo.app.service.base.CrudService;

import java.util.List;

/**
 * Journal service interface.
 *
 * @author johnniang
 * @date 19-4-24
 */
public interface JournalService extends CrudService<Journal, Integer> {

    /**
     * Creates a journal.
     *
     * @param journalParam journal param must not be null
     * @return created journal
     */
    @NonNull
    Journal createBy(@NonNull JournalParam journalParam);

    /**
     * Gets latest journals.
     *
     * @param top max size
     * @return latest journal page
     */
    Page<Journal> pageLatest(int top);

    /**
     * Converts to journal dto.
     *
     * @param journal journal must not be null
     * @return journal dto
     */
    @NonNull
    JournalDTO convertTo(@NonNull Journal journal);

    /**
     * Converts to journal with comment count dto list.
     *
     * @param journals journal list
     * @return journal with comment count dto list
     */
    @NonNull
    List<JournalWithCmtCountDTO> convertToCmtCountDto(@Nullable List<Journal> journals);

    /**
     * Converts to journal with comment count dto page.
     *
     * @param journalPage journal page must not be null
     * @return a page of journal with comment count dto
     */
    @NonNull
    Page<JournalWithCmtCountDTO> convertToCmtCountDto(@NonNull Page<Journal> journalPage);
}