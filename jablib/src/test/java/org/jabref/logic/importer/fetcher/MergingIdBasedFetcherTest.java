package org.jabref.logic.importer.fetcher;

import java.lang.reflect.Method;
import java.util.Optional;

import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergingIdBasedFetcherTest {

    @Test
    void shouldCopyCitationKeyWhenFetcherIsInspire() throws Exception {

        // Create entries
        BibEntry libraryEntry = new BibEntry();
        BibEntry fetchedEntry = new BibEntry();
        fetchedEntry.setCitationKey("InspireKey2024");

        // Create real INSPIRE fetcher
        INSPIREFetcher inspireFetcher =
                new INSPIREFetcher(null);

        // Create merging fetcher
        MergingIdBasedFetcher mergingFetcher =
                new MergingIdBasedFetcher(null);

        // Use reflection to call private method
        Method mergeMethod =
                MergingIdBasedFetcher.class.getDeclaredMethod(
                        "mergeBibEntries",
                        BibEntry.class,
                        BibEntry.class,
                        org.jabref.logic.importer.IdBasedFetcher.class
                );

        mergeMethod.setAccessible(true);

        MergingIdBasedFetcher.FetcherResult result =
                (MergingIdBasedFetcher.FetcherResult)
                        mergeMethod.invoke(
                                mergingFetcher,
                                libraryEntry,
                                fetchedEntry,
                                inspireFetcher
                        );

        Optional<String> citationKey =
                result.mergedEntry().getCitationKey();

        assertTrue(citationKey.isPresent());
        assertEquals("InspireKey2024", citationKey.get());
    }
}
