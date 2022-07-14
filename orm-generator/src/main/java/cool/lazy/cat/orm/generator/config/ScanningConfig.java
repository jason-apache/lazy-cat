package cool.lazy.cat.orm.generator.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : jason.ma
 * @date : 2022/7/13 12:08
 */
public class ScanningConfig {

    private final TableScanningConfig tableScanningConfig;
    private final ColumnScanningConfig columnScanningConfig;

    private ScanningConfig(TableScanningConfig tableScanningConfig, ColumnScanningConfig columnScanningConfig) {
        this.tableScanningConfig = tableScanningConfig;
        this.columnScanningConfig = columnScanningConfig;
    }

    public TableScanningConfig getTableScanningConfig() {
        return tableScanningConfig;
    }

    public ColumnScanningConfig getColumnScanningConfig() {
        return columnScanningConfig;
    }

    public static ScanningConfigBuilder builder() {
        return new ScanningConfigBuilder();
    }

    public static class ScanningConfigBuilder {
        private TableScanningConfig tableScanningConfig = TableScanningConfig.builder().build();
        private ColumnScanningConfig columnScanningConfig = ColumnScanningConfig.builder().build();

        public ScanningConfigBuilder tableScanningConfig(TableScanningConfig tableScanningConfig) {
            this.tableScanningConfig = tableScanningConfig;
            return this;
        }

        public ScanningConfigBuilder columnScanningConfig(ColumnScanningConfig columnScanningConfig) {
            this.columnScanningConfig = columnScanningConfig;
            return this;
        }

        public ScanningConfig build() {
            return new ScanningConfig(tableScanningConfig, columnScanningConfig);
        }
    }

    public static class TableScanningConfig {
        /**
         * 是否跳过视图
         */
        private final boolean skipView;
        /**
         * 扫描策略
         */
        private final List<ScanningStrategy> strategyList;

        public TableScanningConfig(boolean skipView, List<ScanningStrategy> strategyList) {
            this.skipView = skipView;
            this.strategyList = strategyList;
        }

        public boolean isSkipView() {
            return skipView;
        }

        public List<ScanningStrategy> getStrategyList() {
            return strategyList;
        }

        public static TableScanningConfigBuilder builder() {
            return new TableScanningConfigBuilder();
        }

        public static class TableScanningConfigBuilder {
            private boolean skipView = true;
            private List<ScanningStrategy> strategyList;

            public TableScanningConfigBuilder skipView(boolean skipView) {
                this.skipView = skipView;
                return this;
            }

            public TableScanningConfigBuilder scanningStrategy(ScanningStrategy... strategies) {
                this.strategyList = new ArrayList<>(Arrays.asList(strategies));
                return this;
            }

            public TableScanningConfig build() {
                return new TableScanningConfig(skipView, strategyList);
            }
        }
    }

    public static class ColumnScanningConfig {
        /**
         * 扫描策略
         */
        private final List<ScanningStrategy> strategyList;

        public ColumnScanningConfig(List<ScanningStrategy> strategyList) {
            this.strategyList = strategyList;
        }

        public List<ScanningStrategy> getStrategyList() {
            return strategyList;
        }

        public static ColumnScanningConfigBuilder builder() {
            return new ColumnScanningConfigBuilder();
        }

        public static class ColumnScanningConfigBuilder {
            private List<ScanningStrategy> strategyList;

            public ColumnScanningConfigBuilder scanningStrategy(ScanningStrategy... strategies) {
                this.strategyList = new ArrayList<>(Arrays.asList(strategies));
                return this;
            }

            public ColumnScanningConfig build() {
                return new ColumnScanningConfig(strategyList);
            }
        }
    }

    public interface ScanningStrategy {

        enum ScanningModel {
            INCLUDE, EXCLUDE
        }

        boolean match(String objectName);
    }

    public static class PrefixSuffixMatchStrategy implements ScanningStrategy {
        private final String prefix;
        private final String suffix;
        private final ScanningModel model;

        public PrefixSuffixMatchStrategy(String prefix, String suffix, ScanningModel model) {
            this.prefix = prefix;
            this.suffix = suffix;
            this.model = model;
        }

        @Override
        public boolean match(String objectName) {
            boolean matched = false;
            if (null != prefix && null != suffix) {
                matched = objectName.startsWith(prefix) && objectName.endsWith(suffix);
            } else if (null != prefix) {
                matched = objectName.startsWith(prefix);
            } else if (null != suffix) {
                matched = objectName.endsWith(suffix);
            }
            return (model == ScanningModel.INCLUDE) == matched;
        }
    }

    public static class RegexMatchStrategy implements ScanningStrategy {

        private final Pattern pattern;
        private final ScanningModel model;

        public RegexMatchStrategy(String reg, ScanningModel model) {
            this.pattern = Pattern.compile(reg);
            this.model = model;
        }

        @Override
        public boolean match(String objectName) {
            return (model == ScanningModel.INCLUDE) == pattern.matcher(objectName).find();
        }
    }

    public static class EqualStrategy implements ScanningStrategy {

        private final Set<String> words;
        private final ScanningModel model;
        private final boolean ignoreCase;

        public EqualStrategy(ScanningModel model, boolean ignoreCase, String... words) {
            this(words, model, ignoreCase);
        }

        public EqualStrategy(String[] words, ScanningModel model, boolean ignoreCase) {
            this(new HashSet<>(Arrays.asList(words)), model, ignoreCase);
        }

        public EqualStrategy(Set<String> words, ScanningModel model, boolean ignoreCase) {
            this.model = model;
            this.ignoreCase = ignoreCase;
            if (ignoreCase) {
                this.words = words.stream().map(String::toUpperCase).collect(Collectors.toSet());
            } else {
                this.words = words;
            }
        }

        @Override
        public boolean match(String objectName) {
            objectName = ignoreCase ? objectName.toUpperCase() : objectName;
            return (model == ScanningModel.INCLUDE) == words.contains(objectName);
        }
    }
}
