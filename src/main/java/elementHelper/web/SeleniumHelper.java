package elementHelper.web;

import elementHelper.WaitHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumHelper {

  private final WaitHelper waitHelper;
  private final JavaScriptHelper jsHelper;
  private final WebDriver driver;
  private static final boolean IS_DEBUG =
      Boolean.parseBoolean(Dotenv.configure().ignoreIfMissing().load().get("IS_DEBUG"));

  public SeleniumHelper(WebDriver driver) {
    this.driver = driver;
    waitHelper = new WaitHelper(driver);
    jsHelper = new JavaScriptHelper(driver);
  }

  /**
   * Waits until the given element is enabled.
   *
   * @param element The element to wait for.
   * @return The enabled element.
   * @throws InterruptedException If the waiting process is interrupted.
   * @throws RuntimeException If the element is not enabled after the maximum number of tries.
   */
  private WebElement waitTillElementIsEnable(WebElement element) throws InterruptedException {
    int maxTries = 15;
    int numberOfTries = 0;
    while (!isElementEnable(element) && numberOfTries < maxTries) {
      waitHelper.hardWait(numberOfTries);
      numberOfTries++;
    }
    if (!isElementEnable(element)) throw new RuntimeException("Element is not in enable state");
    return element;
  }

  /**
   * Attempts to click on the given element, retrying up to 10 times if the element becomes stale.
   *
   * @param element The element to click on.
   */
  private void clickOnElement(WebElement element) {
    int maxAttempts = 10;
    boolean isStale;

    do {
      try {
        element.click();
        isStale = false;
      } catch (StaleElementReferenceException e) {
        isStale = true;
      } catch (Exception e) {
        e.printStackTrace();
        isStale = true;
      }
      maxAttempts--;
    } while (maxAttempts > 0 && isStale);
  }

  /**
   * Enters the given text into the specified element, waiting for it to be enabled first.
   *
   * @param element The element to enter text into.
   * @param value The text to enter.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  private void enterText(WebElement element, String value) throws InterruptedException {
    var enabledElement = waitTillElementIsEnable(element);
    enabledElement.clear();
    enabledElement.sendKeys(value);
  }

  /**
   * Checks if the given element is enabled.
   *
   * @param element The element to check for enable state.
   * @return True if the element is enabled, false otherwise.
   */
  private boolean isElementEnable(WebElement element) {
    return element.isEnabled();
  }

  /**
   * Checks if the given element is enabled.
   *
   * @param by The locator to find the element.
   * @return True if the element is enabled, false otherwise.
   */
  public boolean isElementEnabled(By by) {
    var elementIsEnable = waitHelper.waitForElementToBeVisible(by);
    return isElementEnable(elementIsEnable);
  }

  /**
   * Checks if the given element is enabled within a specified time.
   *
   * @param by The locator to find the element.
   * @param second The time in seconds to wait for the element to be visible.
   * @return True if the element is enabled, false otherwise.
   */
  public boolean isElementEnabled(By by, int second) {
    var elementIsEnable = waitHelper.waitForElementToBeVisible(by, second);
    return isElementEnable(elementIsEnable);
  }

  /**
   * Scrolls to the specified element and enters the given text into it.
   *
   * @param by The locator to find the element.
   * @param value The text to enter into the element.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void scrollAndEnterText(By by, String value) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementIfNotInView(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    enterText(element, value);
  }

  /**
   * Scrolls to the specified element and enters the given text into it.
   *
   * @param by The locator to find the element.
   * @param value The text to enter into the element.
   * @param second The time in seconds to wait for the element to be visible.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void scrollAndEnterText(By by, String value, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    jsHelper.scrollToElementIfNotInView(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    enterText(element, value);
  }

  /**
   * Enters the given text into the specified element.
   *
   * @param by The locator to find the element.
   * @param value The text to enter into the element.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void enterText(By by, String value) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    enterText(element, value);
  }

  /**
   * Enters the given text into the specified element, waiting for it to be visible within a
   * specified time.
   *
   * @param by The locator to find the element.
   * @param value The text to enter into the element.
   * @param second The time in seconds to wait for the element to be visible.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void enterText(By by, String value, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    enterText(element, value);
  }

  /**
   * Scrolls to the specified element and clicks on it.
   *
   * <p>This method first waits for the element to be clickable, then waits for it to be enabled. It
   * then scrolls to the element if it is not in view, and highlights it if debug mode is enabled.
   * Finally, it attempts to click on the element, retrying up to 10 times if the element becomes
   * stale.
   *
   * @param by The locator to find the element.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void scrollAndClickOn(By by) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by);
    var enabledElement = waitTillElementIsEnable(element);
    jsHelper.scrollToElementIfNotInView(enabledElement);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(enabledElement);
    clickOnElement(enabledElement);
  }

  /**
   * Scrolls to the specified element and clicks on it.
   *
   * <p>This method first waits for the element to be clickable within the specified time. Then, it
   * waits for the element to be enabled. It then scrolls to the element if it is not in view, and
   * highlights it if debug mode is enabled. Finally, it attempts to click on the element, retrying
   * up to 10 times if the element becomes stale.
   *
   * @param by The locator to find the element.
   * @param second The time in seconds to wait for the element to be clickable.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void scrollAndClickOn(By by, int second) throws InterruptedException {
    WebElement element = waitHelper.waitForElementToBeClickable(by, second);
    WebElement elementIsEnable = waitTillElementIsEnable(element);
    jsHelper.scrollToElementIfNotInView(elementIsEnable);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    clickOnElement(elementIsEnable);
  }

  /**
   * Clicks on the element located by the given locator.
   *
   * <p>This method first waits for the element to be clickable, then waits for it to be enabled. If
   * debug mode is enabled, it highlights the element before clicking on it.
   *
   * @param by The locator to find the element.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void clickOn(By by) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by);
    var enabledElement = waitTillElementIsEnable(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(enabledElement);
    clickOnElement(enabledElement);
  }

  /**
   * Clicks on the element located by the given locator, waiting for it to be clickable within the
   * specified time.
   *
   * @param by The locator to find the element.
   * @param second The time in seconds to wait for the element to be clickable.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void clickOn(By by, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeClickable(by, second);
    var enabledElement = waitTillElementIsEnable(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(enabledElement);
    clickOnElement(enabledElement);
  }

  /**
   * Checks if an element is displayed on the page.
   *
   * @param by The locator to find the element.
   * @return True if the element is displayed, false otherwise.
   */
  public boolean isElementDisplayed(By by) {
    WebElement element = null;
    try {
      element = waitHelper.waitForElementToBeVisible(by);
      if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    } catch (Exception _) {
    }
    return element != null;
  }

  /**
   * Checks if an element is displayed on the page within the specified time.
   *
   * @param by The locator to find the element.
   * @param second The time in seconds to wait for the element to be displayed.
   * @return True if the element is displayed, false otherwise.
   */
  public boolean isElementDisplayed(By by, int second) {
    WebElement element = null;
    try {
      element = waitHelper.waitForElementToBeVisible(by, second);
      if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    } catch (Exception _) {
    }
    return element != null;
  }

  /**
   * Selects an option from a dropdown menu using the visible text.
   *
   * @param by The locator to find the dropdown element.
   * @param text The visible text of the option to select.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void selectOptionByText(By by, String text) throws InterruptedException {
    var dropdownElement = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementIfNotInView(dropdownElement);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(dropdownElement);
    new Select(dropdownElement).selectByVisibleText(text);
  }

  /**
   * Selects an option from a dropdown menu using the visible text, waiting for the element to be
   * visible within the specified time.
   *
   * @param by The locator to find the dropdown element.
   * @param text The visible text of the option to select.
   * @param second The time in seconds to wait for the element to be visible.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void selectOptionByText(By by, String text, int second) throws InterruptedException {
    var dropdownElement = waitHelper.waitForElementToBeVisible(by, second);
    jsHelper.scrollToElementIfNotInView(dropdownElement);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(dropdownElement);
    new Select(dropdownElement).selectByVisibleText(text);
  }

  /**
   * Selects an option from a dropdown menu using the specified value.
   *
   * @param by The locator to find the dropdown element.
   * @param value The value of the option to select.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void selectOptionByValue(By by, String value) throws InterruptedException {
    var dropdownElement = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementIfNotInView(dropdownElement);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(dropdownElement);
    new Select(dropdownElement).selectByValue(value);
  }

  /**
   * Selects an option from a dropdown menu by its value.
   *
   * @param by the locator strategy to find the dropdown element
   * @param value the value of the option to select
   * @param timeout the maximum time to wait for the element to be visible (in seconds)
   * @throws InterruptedException if the thread is interrupted while waiting for the element
   */
  public void selectOptionByValue(By by, String value, int timeout) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, timeout);
    jsHelper.scrollToElementIfNotInView(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    new Select(element).selectByValue(value);
  }

  /**
   * Selects an option from a dropdown menu using the specified index.
   *
   * @param by The locator to find the dropdown element.
   * @param index The index of the option to select.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void selectOptionByIndex(By by, int index) throws InterruptedException {
    var dropdownElement = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementIfNotInView(dropdownElement);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(dropdownElement);
    new Select(dropdownElement).selectByIndex(index);
  }

  /**
   * Selects an option from a select element by its index.
   *
   * @param by The locator strategy used to find the select element.
   * @param index The index of the option to select.
   * @param second The maximum time to wait for the element to be visible.
   * @throws InterruptedException If the wait times out or is interrupted.
   */
  public void selectOptionByIndex(By by, int index, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    new Select(element).selectByIndex(index);
  }

  /**
   * Retrieves all option texts from a dropdown menu.
   *
   * @param by The locator strategy to find the dropdown element.
   * @return A list of strings representing the text of all options in the dropdown.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public List<String> getAllOptionText(By by) throws InterruptedException {
    var dropdownElement = waitHelper.waitForElementToBeVisible(by);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(dropdownElement);
    return new Select(dropdownElement).getOptions().stream().map(WebElement::getText).toList();
  }

  /**
   * Retrieves all option texts from a dropdown menu.
   *
   * @param by The locator strategy to find the dropdown element.
   * @param second The maximum time to wait for the element to be visible (in seconds).
   * @return A list of strings representing the text of all options in the dropdown.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public List<String> getAllOptionText(By by, int second) throws InterruptedException {
    WebElement dropdownElement = waitHelper.waitForElementToBeVisible(by, second);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(dropdownElement);
    return new Select(dropdownElement).getOptions().stream().map(WebElement::getText).toList();
  }

  /**
   * Switches to an iframe using the provided locator.
   *
   * @param by The locator strategy to find the iframe element.
   */
  public void switchToIframe(By by) {
    waitHelper.waitForFrameToBeAvailableAndSwitchToIt(by);
  }

  /**
   * Switches to an iframe using the provided locator, waiting for the specified time.
   *
   * @param by The locator strategy to find the iframe element.
   * @param second The maximum time to wait for the iframe to be available (in seconds).
   */
  public void switchToIframe(By by, int second) {
    waitHelper.waitForFrameToBeAvailableAndSwitchToIt(by, second);
  }

  /**
   * Checks if a radio button is selected.
   *
   * @param by The locator strategy to find the radio button element.
   * @return True if the radio button is selected, false otherwise.
   */
  public boolean isRadioButtonSelected(By by) {
    return waitHelper.waitForElementToBeVisible(by).isSelected();
  }

  /**
   * Checks if a radio button is selected.
   *
   * @param by The locator of the radio button element.
   * @param second The time in seconds to wait for the element to be visible.
   * @return True if the radio button is selected, false otherwise.
   */
  public boolean isRadioButtonSelected(By by, int second) {
    return waitHelper.waitForElementToBeVisible(by, second).isSelected();
  }

  /**
   * Checks if a checkbox button is selected.
   *
   * @param by The locator strategy to find the checkbox button element.
   * @return True if the checkbox button is selected, false otherwise.
   */
  public boolean isCheckBoxButtonSelected(By by) {
    return waitHelper.waitForElementToBeVisible(by).isSelected();
  }

  /**
   * Checks if a checkbox button is selected.
   *
   * @param by The locator strategy to find the checkbox button element.
   * @param second The time in seconds to wait for the element to be visible.
   * @return True if the checkbox button is selected, false otherwise.
   */
  public boolean isCheckBoxButtonSelected(By by, int second) {
    return waitHelper.waitForElementToBeVisible(by, second).isSelected();
  }

  /**
   * Clicks on an element using JavaScript.
   *
   * @param by The locator strategy to find the element.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void clickOnElementUsingJavaScript(By by) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    jsHelper.javaScriptClickOn(element);
  }

  /**
   * Clicks on an element using JavaScript.
   *
   * @param by The locator to find the element.
   * @param second The time in seconds to wait for the element to be visible.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public void clickOnElementUsingJavaScript(By by, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    jsHelper.javaScriptClickOn(element);
  }

  /**
   * This method is used to enter text into a web element using JavaScript. It waits for the element
   * to be visible before performing the action.
   *
   * @param by The By locator strategy to locate the element.
   * @param value The text to be entered into the element.
   */
  public void enterTextUsingJavaScript(By by, String value) {
    var element = waitHelper.waitForElementToBeVisible(by);
    jsHelper.javaScriptEnterText(element, value);
  }

  /**
   * Enters the given text into the specified element using JavaScript, waiting for the element to
   * be visible within the specified time.
   *
   * @param by The By locator strategy to locate the element.
   * @param value The text to be entered into the element.
   * @param second The maximum time to wait for the element to be visible (in seconds).
   */
  public void enterTextUsingJavaScript(By by, String value, int second) {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    jsHelper.javaScriptEnterText(element, value);
  }

  /**
   * Scrolls to the element specified by the given locator.
   *
   * @param by the locator of the element to scroll to
   */
  public void scrollToTheElement(By by) {
    var element = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementCenter(element);
  }

  /**
   * Scrolls to the specified element on the page.
   *
   * @param by the locator strategy to find the element
   * @param second the maximum time to wait for the element to be visible
   */
  public void scrollToTheElement(By by, int second) {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    jsHelper.scrollToElementCenter(element);
  }

  /**
   * Retrieves the text from an element located by the given By locator.
   *
   * <p>This method waits for the element to be visible before attempting to retrieve its text.
   *
   * @param by The By locator strategy to find the element.
   * @return The text of the element, trimmed of any leading or trailing whitespace.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public String getText(By by) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by);
    jsHelper.scrollToElementIfNotInView(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    return element.getText().trim();
  }

  /**
   * Retrieves the text from an element located by the given By locator.
   *
   * @param by The By locator strategy to find the element.
   * @param second The maximum time to wait for the element to be visible (in seconds).
   * @return The text of the element, trimmed of any leading or trailing whitespace.
   * @throws InterruptedException If the waiting process is interrupted.
   */
  public String getText(By by, int second) throws InterruptedException {
    var element = waitHelper.waitForElementToBeVisible(by, second);
    jsHelper.scrollToElementIfNotInView(element);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    return element.getText().trim();
  }

  /**
   * Retrieves the text from multiple elements located by the given By locator.
   *
   * <p>This method waits for all elements to be visible before attempting to retrieve their text.
   *
   * @param by The By locator strategy to find the elements.
   * @return A list of strings representing the text of all elements, trimmed of any leading or
   *     trailing whitespace.
   */
  public List<String> getAllElementsText(By by) {
    var elements = waitHelper.waitForAllElementToBeVisible(by);
    if (IS_DEBUG)
      try {
        for (WebElement element : elements) jsHelper.javaScriptHighlightElement(element);
      } catch (Exception _) {
      }
    return elements.stream().map(x -> x.getText().trim().replace("\n", "")).toList();
  }

  /**
   * Retrieves the text from multiple elements located by the given By locator.
   *
   * <p>This method waits for all elements to be visible within the specified time before attempting
   * to retrieve their text.
   *
   * @param by The By locator strategy to find the elements.
   * @param second The maximum time to wait for the elements to be visible (in seconds).
   * @return A list of strings representing the text of all elements, trimmed of any leading or
   *     trailing whitespace.
   */
  public List<String> getAllElementsText(By by, int second) {
    var elements = waitHelper.waitForAllElementToBeVisible(by, second);
    if (IS_DEBUG) {
      try {
        for (WebElement element : elements) jsHelper.javaScriptHighlightElement(element);
      } catch (Exception _) {
      }
    }
    return elements.stream().map(x -> x.getText().trim().replace("\n", "")).toList();
  }

  /**
   * Waits until the page is loaded properly. This method uses the WaitHelper class to wait for the
   * page content to be loaded.
   *
   * @throws InterruptedException if the thread is interrupted while waiting
   */
  public void waitTillPageLoadedProperly() throws InterruptedException {
    waitHelper.waitForPageContentLoaded();
  }

  /**
   * Retrieves the title of the current page.
   *
   * <p>This method waits for the page to be fully loaded before attempting to retrieve its title.
   *
   * @return The title of the current page.
   * @throws InterruptedException if the thread is interrupted while waiting for the page to load.
   */
  public String getPageTitle() throws InterruptedException {
    waitTillPageLoadedProperly();
    return driver.getTitle();
  }

  /**
   * Checks if an element located by the given By locator has a specific CSS class.
   *
   * @param by The By locator strategy to find the element.
   * @param cssValue The CSS class value to check for.
   * @return True if the element has the specified CSS class, false otherwise.
   */
  public boolean isElementHaveGivenClass(By by, String cssValue) {
    var element = waitHelper.waitForElementToBeVisible(by);
    var classAttribute = element.getAttribute("class");
    return classAttribute != null && classAttribute.contains(cssValue);
  }

  /**
   * Uploads a file to the specified element.
   *
   * @param by The By locator strategy to find the element.
   * @param fileName The name of the file to be uploaded.
   * @throws InterruptedException if the thread is interrupted while waiting for the element to be
   *     visible.
   */
  public void uploadFile(By by, String fileName) throws InterruptedException {
    var file = FileHelper.getUploadFilesFullPath(fileName);
    var element = waitHelper.waitForElementPresenceInDOM(by);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    if (Objects.equals(element.getAttribute("type"), "file")) element.sendKeys(file);
    else jsHelper.uploadFile(element, file, false);
  }

  /**
   * Uploads a file to the specified element.
   *
   * @param by The By locator strategy to find the element.
   * @param fileName The name of the file to be uploaded.
   * @param second The number of seconds to wait for the element to be visible.
   * @throws InterruptedException if the thread is interrupted while waiting for the element to be
   *     visible.
   */
  public void uploadFile(By by, String fileName, int second) throws InterruptedException {
    var file = FileHelper.getUploadFilesFullPath(fileName);
    var element = waitHelper.waitForElementPresenceInDOM(by, second);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    if (Objects.equals(element.getAttribute("type"), "file")) element.sendKeys(file);
    else jsHelper.uploadFile(element, file, false);
  }

  /**
   * Uploads multiple files to the specified element.
   *
   * @param chooseFile The By locator strategy to find the element.
   * @param fileNames The names of the files to be uploaded.
   * @throws InterruptedException if the thread is interrupted while waiting for the element to be
   *     visible.
   */
  public void uploadFile(By chooseFile, List<String> fileNames) throws InterruptedException {
    var filePathForAllFiles = FileHelper.getConcatenatedPath(fileNames);
    var element = waitHelper.waitForElementPresenceInDOM(chooseFile);
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    var isTypeAttributeHasFile = Objects.equals(element.getAttribute("type"), "file");
    var isMultiplePropertyAvailable = element.getDomProperty("multiple") != null;
    var isMultiplePropertyHaveNotFalseValue = Objects.equals(element.getAttribute("type"), "false");
    if (isTypeAttributeHasFile
        && isMultiplePropertyAvailable
        && isMultiplePropertyHaveNotFalseValue) {
      element.sendKeys(filePathForAllFiles);
    } else jsHelper.uploadFile(element, filePathForAllFiles, true);
  }

  /**
   * Uploads multiple files to the specified element.
   *
   * @param by The By locator strategy to find the element.
   * @param fileNames The names of the files to be uploaded.
   * @param second The number of seconds to wait for the element to be visible.
   * @throws InterruptedException if the thread is interrupted while waiting for the element to be
   *     visible.
   */
  public void uploadFile(By by, List<String> fileNames, int second) throws InterruptedException {
    var filePathForAllFiles = FileHelper.getConcatenatedPath(fileNames);
    var element = waitHelper.waitForElementPresenceInDOM(by, second);
    var isTypeAttributeHasFile = Objects.equals(element.getAttribute("type"), "file");
    var isMultiplePropertyAvailable = element.getDomProperty("multiple") != null;
    var isMultiplePropertyHaveNotFalseValue = Objects.equals(element.getAttribute("type"), "false");
    if (IS_DEBUG) jsHelper.javaScriptHighlightElement(element);
    if (isTypeAttributeHasFile
        && isMultiplePropertyAvailable
        && isMultiplePropertyHaveNotFalseValue) {
      element.sendKeys(filePathForAllFiles);
    } else jsHelper.uploadFile(element, filePathForAllFiles, true);
  }

  @Builder(builderMethodName = "initiateDownloadAndVerifyWithExpectedMessageBuilder")
  private Pair<Boolean, String> initiateDownloadAndVerifyWithExpectedMessage(
      By locator, String fileName, Integer downloadTimeout, Integer elementVisibilityTimeout)
      throws InterruptedException {

    // Check required parameters
    if (locator==null) throw new RuntimeException("locator cannot be null");
    if (fileName==null) throw new RuntimeException("fileName cannot be null");

    // Optional parameters with default values
    int effectiveDownloadTimeout = Optional.ofNullable(downloadTimeout).orElse(15);
    int effectiveElementVisibilityTimeout =
        Optional.ofNullable(elementVisibilityTimeout).orElse(15);

    // Perform file operations
    FileHelper.deleteFile(fileName);
    scrollAndClickOn(locator, effectiveElementVisibilityTimeout);
    return FileHelper.isFileExists(fileName, effectiveDownloadTimeout);
  }

  @Builder(builderMethodName = "initiateDownloadAndVerifyBuilder")
  private Boolean initiateDownloadAndVerify(
      By locator, String fileName, Integer downloadTimeout, Integer elementVisibilityTimeout)
      throws InterruptedException {

    // Check required parameters
    if (locator==null) throw new RuntimeException("locator cannot be null");
    if (fileName==null) throw new RuntimeException("fileName cannot be null");

    // Optional parameters with default values
    int effectiveDownloadTimeout = Optional.ofNullable(downloadTimeout).orElse(15);
    int effectiveElementVisibilityTimeout =
        Optional.ofNullable(elementVisibilityTimeout).orElse(15);

    // Perform file operations
    FileHelper.deleteFile(fileName);
    scrollAndClickOn(locator, effectiveElementVisibilityTimeout);
    return FileHelper.isFileExists(fileName, effectiveDownloadTimeout).getLeft();
  }
}
