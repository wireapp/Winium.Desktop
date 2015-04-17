﻿namespace WindowsFormsTestApplication.Tests
{
    #region using

    using NUnit.Framework;

    #endregion

    public class TakeScreenshotTests : BaseTest
    {
        #region Public Methods and Operators

        [Test]
        public void IsSelectedListItem()
        {
            var screenshot = this.Driver.GetScreenshot().ToString();
            Assert.That(screenshot.Length, Is.GreaterThan(0));
        }

        #endregion
    }
}
