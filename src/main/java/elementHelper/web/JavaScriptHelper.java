package elementHelper.web;

import elementHelper.WaitHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptHelper {

  private final JavascriptExecutor js;
  private final WaitHelper waitHelper;

  public JavaScriptHelper(WebDriver driver) {
    js = (JavascriptExecutor) driver;
    waitHelper = new WaitHelper(driver);
  }

  // Using this we check element displayed in viewport
  private boolean isElementInViewport(WebElement element) {
    var jsQuery =
        """
          var rect = arguments[0].getBoundingClientRect();
          return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));
        """;
    return (Boolean) js.executeScript(jsQuery, element);
  }

  // used this method when element is not in view port
  void scrollToElementCenter(WebElement element) {
    var jsQuery =
        """
    var elementRect = arguments[0].getBoundingClientRect();
    var absoluteElementTop = elementRect.top + window.pageYOffset;
    var middle = absoluteElementTop - (window.innerHeight / 2);
    window.scrollTo(0, middle);
    """;
    js.executeScript(jsQuery, element);
  }

  // use this method to check and ensure element is in view port
  void scrollToElementIfNotInView(WebElement element) {
    if (!isElementInViewport(element)) {
      scrollToElementCenter(element);
    }
  }

  void javaScriptEnterText(WebElement element, String value) {
    js.executeScript("arguments[0].value='arguments[1];", element, value);
  }

  void javaScriptClickOn(WebElement element) {
    js.executeScript("arguments[0].click();", element);
  }

  void javaScriptHighlightElement(WebElement element) throws InterruptedException {
    js.executeScript("arguments[0].style.border='3px solid red'", element);
    waitHelper.hardWait(2);
  }

  public void uploadFile(WebElement element, String filePath, boolean isMultiple) {
    var jsQuery =
        """
        var target = arguments[0],
        offsetX = arguments[1],
        offsetY = arguments[2],
        document = target.ownerDocument || document,
        window = document.defaultView || window;

        var input = document.createElement('INPUT');
        input.type = 'file';
        """
            + (isMultiple ? "input.multiple = true;" : "")
            + """
        input.onchange = function () {
         var rect = target.getBoundingClientRect(),
          x = rect.left + (offsetX || (rect.width >> 1)),
          y = rect.top + (offsetY || (rect.height >> 1)),
          dataTransfer = { files: this.files };

        ['dragenter', 'dragover', 'drop'].forEach(function (name) {
          var evt = document.createEvent('MouseEvent');
          evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);
          evt.dataTransfer = dataTransfer;
          target.dispatchEvent(evt);
        });

        setTimeout(function () { document.body.removeChild(input); }, 25);
      };
      document.body.appendChild(input);
      return input;
      """;

    var fileInput = (WebElement) js.executeScript(jsQuery, element, 0, 0);
    if (fileInput != null) fileInput.sendKeys(filePath);
    else throw new RuntimeException("File input button not available for upload file");
  }
}
