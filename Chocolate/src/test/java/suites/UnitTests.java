package suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages({"chocolate"})
@SuiteDisplayName("All unit tests")
@IncludeTags({"UnitTests"})
public class UnitTests {}
